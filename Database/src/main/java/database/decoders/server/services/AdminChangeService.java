package database.decoders.server.services;

import buffer.DynamicByteBuffer;
import database.DatabaseServer;
import database.decoders.DecoderService;
import io.netty.channel.Channel;

public class AdminChangeService implements DecoderService {

    private static final byte ADD_ADMIN = 0;
    private static final byte REMOVE_ADMIN = 1;
    private static final byte CHANGE_TOKEN = 2;

    @Override
    public void decode(Channel channel, DynamicByteBuffer buffer) {
        DatabaseServer database = channel.attr(DatabaseServer.KEY).get();
        byte type = buffer.readByte();
        String username = buffer.readString();
        String token = buffer.readString(); // Must verify token before we delete admin

        switch (type) {
            case ADD_ADMIN -> database.createAdministrator(username);
            case REMOVE_ADMIN -> database.removeAdministrator(token, username);
            case CHANGE_TOKEN -> database.changeAdministratorToken(token, username);
        }
    }

    @Override
    public int identifier() {
        return 0;
    }
}
