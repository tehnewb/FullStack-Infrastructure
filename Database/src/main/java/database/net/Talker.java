package database.net;

import collections.array.ResizingArray;
import collections.queue.ShortUUIDQueue;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import io.netty.util.CharsetUtil;
import service.ServiceLoader;

public class Talker {
    public static final AttributeKey<Talker> KEY = AttributeKey.valueOf("Talker");
    private static RequestService[] services;

    static {
        ServiceLoader<RequestService> loader = ServiceLoader
                .load("database.net.service", RequestService.class, f -> true);

        services = new RequestService[loader.size()];
        for (RequestService service : loader)
            services[service.ID()] = service;
    }

    private Channel channel;
    private ShortUUIDQueue freeUUID;
    private ResizingArray<Request> requests;

    public Talker(Channel channel) {
        this.channel = channel;
        this.requests = new ResizingArray<>(8);
        this.freeUUID = new ShortUUIDQueue();
    }

    public static RequestService getService(int ID) {
        return services[ID];
    }

    public void request(byte requestID, String identifiers, Callback callback) {
        ByteBuf buffer = Unpooled.buffer();

        short UUID = freeUUID.pop(); // retrieve unique ID
        requests.set(UUID, new Request(UUID, callback));

        buffer.writeByte(requestID);
        buffer.writeShort(UUID);
        buffer.writeShort(identifiers.length());
        buffer.writeCharSequence(identifiers, CharsetUtil.UTF_8);

        getService(requestID).generateRequest(identifiers, buffer);

        this.channel.writeAndFlush(buffer); // write buffer to server
    }

    public void respond(Response response) {
        Request request = requests.get(response.UUID()); // get request made corresponding to the UUID
        if (request == null)
            return; // no request exists for some reason

        request.callback().handle(response); // callback made by the request is submitted
        requests.set(response.UUID(), null); // make request no longer available
        freeUUID.push(response.UUID()); // we can now reuse the UUID
    }

}
