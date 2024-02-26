package database.server.decoders;

import buffer.DynamicByteBuffer;
import io.netty.channel.Channel;
import service.ServiceLoader;

public interface DecoderService {

    static DecoderService[] loadServices(String pkg) {
        ServiceLoader<DecoderService> loader = ServiceLoader.load(pkg, DecoderService.class);
        DecoderService[] services = new DecoderService[loader.size()];
        loader.forEach(service -> services[service.identifier()] = service);
        return services;
    }

    void decode(Channel channel, DynamicByteBuffer buffer);

    int identifier();
}
