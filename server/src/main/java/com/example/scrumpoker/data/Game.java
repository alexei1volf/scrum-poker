// automatically generated by the FlatBuffers compiler, do not modify

package com.example.scrumpoker.data;

import com.google.flatbuffers.BaseVector;
import com.google.flatbuffers.Constants;
import com.google.flatbuffers.FlatBufferBuilder;
import com.google.flatbuffers.Table;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

@SuppressWarnings("unused")
public final class Game extends Table {
  public static void ValidateVersion() { Constants.FLATBUFFERS_2_0_0(); }
  public static Game getRootAsGame(ByteBuffer _bb) { return getRootAsGame(_bb, new Game()); }
  public static Game getRootAsGame(ByteBuffer _bb, Game obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__assign(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public void __init(int _i, ByteBuffer _bb) { __reset(_i, _bb); }
  public Game __assign(int _i, ByteBuffer _bb) { __init(_i, _bb); return this; }

  public com.example.scrumpoker.data.Player players(int j) { return players(new com.example.scrumpoker.data.Player(), j); }
  public com.example.scrumpoker.data.Player players(com.example.scrumpoker.data.Player obj, int j) { int o = __offset(4); return o != 0 ? obj.__assign(__indirect(__vector(o) + j * 4), bb) : null; }
  public int playersLength() { int o = __offset(4); return o != 0 ? __vector_len(o) : 0; }
  public com.example.scrumpoker.data.Player.Vector playersVector() { return playersVector(new com.example.scrumpoker.data.Player.Vector()); }
  public com.example.scrumpoker.data.Player.Vector playersVector(com.example.scrumpoker.data.Player.Vector obj) { int o = __offset(4); return o != 0 ? obj.__assign(__vector(o), 4, bb) : null; }

  public static int createGame(FlatBufferBuilder builder,
      int playersOffset) {
    builder.startTable(1);
    Game.addPlayers(builder, playersOffset);
    return Game.endGame(builder);
  }

  public static void startGame(FlatBufferBuilder builder) { builder.startTable(1); }
  public static void addPlayers(FlatBufferBuilder builder, int playersOffset) { builder.addOffset(0, playersOffset, 0); }
  public static int createPlayersVector(FlatBufferBuilder builder, int[] data) { builder.startVector(4, data.length, 4); for (int i = data.length - 1; i >= 0; i--) builder.addOffset(data[i]); return builder.endVector(); }
  public static void startPlayersVector(FlatBufferBuilder builder, int numElems) { builder.startVector(4, numElems, 4); }
  public static int endGame(FlatBufferBuilder builder) {
    int o = builder.endTable();
    return o;
  }
  public static void finishGameBuffer(FlatBufferBuilder builder, int offset) { builder.finish(offset); }
  public static void finishSizePrefixedGameBuffer(FlatBufferBuilder builder, int offset) { builder.finishSizePrefixed(offset); }

  public static final class Vector extends BaseVector {
    public Vector __assign(int _vector, int _element_size, ByteBuffer _bb) { __reset(_vector, _element_size, _bb); return this; }

    public Game get(int j) { return get(new Game(), j); }
    public Game get(Game obj, int j) {  return obj.__assign(Table.__indirect(__element(j), bb), bb); }
  }
}

