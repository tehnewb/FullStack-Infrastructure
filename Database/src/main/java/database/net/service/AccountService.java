package database.net.service;

import database.net.RequestService;
import database.resource.Database;
import database.resource.strategies.AccountDatabaseStrategy;
import io.netty.buffer.ByteBuf;

public class AccountService implements RequestService {

    private static final Database<String, Object> DATABASE = new Database(new AccountDatabaseStrategy());

    @Override
    public ByteBuf generateRequest(String identifiers, ByteBuf message) {
        return null;
    }

    @Override
    public ByteBuf generateResponse(short UUID, String identifiers, ByteBuf message) {
        return null;
    }

    @Override
    public int ID() {
        return ACCOUNT_INFO;
    }
}
