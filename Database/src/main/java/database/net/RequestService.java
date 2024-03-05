package database.net;

import io.netty.buffer.ByteBuf;

public interface RequestService {

    byte BASIC_INFO = 0;
    byte ACCOUNT_INFO = 1;

    ByteBuf generateRequest(String identifiers, ByteBuf message);

    ByteBuf generateResponse(short UUID, String identifiers, ByteBuf message);

    int ID();
}
