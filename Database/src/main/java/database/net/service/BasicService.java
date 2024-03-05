package database.net.service;

import database.net.RequestService;
import database.net.Server;
import database.resource.Database;
import database.resource.strategies.BasicDatabaseStrategy;
import files.parsers.FlatYaml;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

import java.util.stream.Stream;

public class BasicService implements RequestService {

    private static final Database<String, Object> DATABASE = new Database(new BasicDatabaseStrategy());

    @Override
    public ByteBuf generateRequest(String identifiers, ByteBuf message) {
        return message;
    }

    @Override
    public ByteBuf generateResponse(short UUID, String identifiers, ByteBuf message) {

        FlatYaml parser = new FlatYaml();
        Stream.of(identifiers.split(",".replace(" ", "")))
                .forEach(i -> parser.put(i, DATABASE.get(i)));

        String export = parser.export();

        ByteBuf response = Unpooled.buffer();
        response.writeShort(UUID);
        response.writeShort(export.length());
        response.writeCharSequence(export, CharsetUtil.UTF_8);
        return response;
    }

    @Override
    public int ID() {
        return BASIC_INFO;
    }
}
