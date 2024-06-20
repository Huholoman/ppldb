package org.epptec.ppldb.common.inmemory.storage;

import org.epptec.ppldb.common.inmemory.storage.exceptions.NodeNotFoundException;

public interface Storage<Identifier, Value> {
    Value getValue(Identifier identifier) throws NodeNotFoundException;
    void addValue(Identifier identifier, Value value);
    void removeValue(Identifier identifier) throws NodeNotFoundException;
}
