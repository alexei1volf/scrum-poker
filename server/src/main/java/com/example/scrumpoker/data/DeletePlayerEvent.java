// automatically generated by the FlatBuffers compiler, do not modify

package com.example.scrumpoker.data;

import com.google.flatbuffers.BaseVector;
import com.google.flatbuffers.Constants;
import com.google.flatbuffers.FlatBufferBuilder;
import com.google.flatbuffers.Table;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

@SuppressWarnings("unused")
public final class DeletePlayerEvent extends Table {
  public static void ValidateVersion() { Constants.FLATBUFFERS_2_0_0(); }
  public static DeletePlayerEvent getRootAsDeletePlayerEvent(ByteBuffer _bb) { return getRootAsDeletePlayerEvent(_bb, new DeletePlayerEvent()); }
  public static DeletePlayerEvent getRootAsDeletePlayerEvent(ByteBuffer _bb, DeletePlayerEvent obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__assign(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public void __init(int _i, ByteBuffer _bb) { __reset(_i, _bb); }
  public DeletePlayerEvent __assign(int _i, ByteBuffer _bb) { __init(_i, _bb); return this; }

  public String id() { int o = __offset(4); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer idAsByteBuffer() { return __vector_as_bytebuffer(4, 1); }
  public ByteBuffer idInByteBuffer(ByteBuffer _bb) { return __vector_in_bytebuffer(_bb, 4, 1); }

  public static int createDeletePlayerEvent(FlatBufferBuilder builder,
      int idOffset) {
    builder.startTable(1);
    DeletePlayerEvent.addId(builder, idOffset);
    return DeletePlayerEvent.endDeletePlayerEvent(builder);
  }

  public static void startDeletePlayerEvent(FlatBufferBuilder builder) { builder.startTable(1); }
  public static void addId(FlatBufferBuilder builder, int idOffset) { builder.addOffset(0, idOffset, 0); }
  public static int endDeletePlayerEvent(FlatBufferBuilder builder) {
    int o = builder.endTable();
    return o;
  }

  public static final class Vector extends BaseVector {
    public Vector __assign(int _vector, int _element_size, ByteBuffer _bb) { __reset(_vector, _element_size, _bb); return this; }

    public DeletePlayerEvent get(int j) { return get(new DeletePlayerEvent(), j); }
    public DeletePlayerEvent get(DeletePlayerEvent obj, int j) {  return obj.__assign(Table.__indirect(__element(j), bb), bb); }
  }
}

