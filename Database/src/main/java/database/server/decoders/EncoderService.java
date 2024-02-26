package database.server.decoders;

import buffer.DynamicByteBuffer;
import io.netty.channel.Channel;
import service.ServiceLoader;

public interface EncoderService {

    static EncoderService[] loadServices(String pkg) {
        ServiceLoader<EncoderService> loader = ServiceLoader.load(pkg, EncoderService.class);
        EncoderService[] services = new EncoderService[loader.size()];
        loader.forEach(service -> services[service.identifier()] = service);
        return services;
    }

    void encode(Channel channel, DynamicByteBuffer buffer);

    int identifier();
}
