package org.epptec.ppldb.common.inmemory.storage;

import org.epptec.ppldb.common.inmemory.storage.exceptions.NodeNotFoundException;
import org.epptec.ppldb.common.inmemory.storage.exceptions.ValueExistsException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class PureStorage<Identifier, Key, Value> implements Storage<Identifier, Value> {
    private final Node<Key, Value> root;
    private final Function<Identifier, List<Key>> identifierToKeyList;

    public PureStorage(Function<Identifier, List<Key>> identifierToKeyList) {
        root = new Node<>();
        this.identifierToKeyList = identifierToKeyList;
    }

    public Value getValue(Identifier identifier) throws NodeNotFoundException {
        return root.getValue(identifierToKeyList.apply(identifier));
    }

    public void addValue(Identifier identifier, Value value) throws ValueExistsException {
        root.addValue(identifierToKeyList.apply(identifier), value);
    }

    public void removeValue(Identifier identifier) throws NodeNotFoundException {
        root.removeValue(identifierToKeyList.apply(identifier));
    }

    private static class Node<Key, Value> {

        private final Map<Key, Node<Key, Value>> children = new HashMap<>();
        private Optional<Value> value;

        public Node() {
            this.value = Optional.empty();
        }

        public Value getValue(List<Key> keys) throws NodeNotFoundException {
            if (keys.isEmpty()) {
                if (value.isEmpty()) {
                    throw new NodeNotFoundException();
                }
                return value.get();
            }

            var key = keys.get(0);
            if (!children.containsKey(key)) {
                throw new NodeNotFoundException();
            }

            return children.get(key).getValue(keys.subList(1, keys.size()));
        }

        public void addValue(List<Key> keys, Value value) throws ValueExistsException {
            if (keys.isEmpty()) {
                if (this.value.isPresent()) {
                    throw new ValueExistsException();
                }
                this.value = Optional.of(value);
                return;
            }

            var key = keys.get(0);
            if (!children.containsKey(key)) {
                children.put(key, new Node<>());
            }

            children.get(key).addValue(keys.subList(1, keys.size()), value);
        }

        public void removeValue(List<Key> keys) throws NodeNotFoundException {
            if (keys.isEmpty()) {
                throw new IllegalArgumentException("Keys cannot be empty");
            }

            var key = keys.get(0);
            if (keys.isEmpty()) {
                children.get(key).removeValue(keys.subList(1, keys.size()));
                return;
            }

            if (children.containsKey(key)) {
                children.remove(key);
                return;
            }

            throw new NodeNotFoundException();
        }
    }

}
