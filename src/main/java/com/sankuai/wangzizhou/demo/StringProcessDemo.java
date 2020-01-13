package com.sankuai.wangzizhou.demo;

import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.StreamSupport;

public class StringProcessDemo {
    public static void main(String[] args) {
        final String inferno =
                " Nel   mezzo del cammin  di nostra  vita " +
                        "mi  ritrovai in una  selva oscura" +
                        " ché la  dritta via era   smarrita ";
        stringProcess(inferno);
    }

    public static void stringProcess(String str) {

        if (str == null || "".equals(str)) {
            System.out.println("unable to process.");
            return;
        }
        var spliterator = new WordCounterSpliterator(str);
        var stream = StreamSupport.stream(spliterator, true);
        var res = stream
                .reduce(new ImmuWordCounter(0, true),
                        ImmuWordCounter::accumulateChar,
                        ImmuWordCounter::combine)
                .getCnt();
        System.out.println("word count " + res);

    }
    final static class ImmuWordCounter {
        private final int cnt;
        private final boolean lastProcessedIsSpace;

        public ImmuWordCounter(int cnt, boolean lastProcessedIsSpace) {
            this.cnt = cnt;
            this.lastProcessedIsSpace = lastProcessedIsSpace;
        }

        public ImmuWordCounter accumulateChar(Character c) {
            if(Character.isWhitespace(c)) {
                return lastProcessedIsSpace ?
                        this : new ImmuWordCounter(cnt, true);
            } else {
                return lastProcessedIsSpace ?
                        new ImmuWordCounter(cnt + 1, false) : this;
            }
        }

        public ImmuWordCounter combine(ImmuWordCounter counter) {
            return new ImmuWordCounter(cnt + counter.cnt, false);
            // state ignored when combine
        }

        public int getCnt() {
            return cnt;
        }
    }

    final static class WordCounterSpliterator implements Spliterator<Character> {
        private final String immuString;
        private int currentPos;

        public WordCounterSpliterator(String string) {
            this.immuString = string;
        }

        @Override
        public boolean tryAdvance(Consumer<? super Character> action) {
            action.accept(immuString.charAt(currentPos++));
            return currentPos < immuString.length();
        }

        @Override
        public Spliterator<Character> trySplit() {
            var len = immuString.length();
            var nowSize = len - currentPos;
            if(nowSize < 16) {
                return null; // no value for further split
            }
            for(var cutPos = nowSize / 2 + currentPos; cutPos < len; cutPos++) {
                if(Character.isWhitespace(immuString.charAt(cutPos))) {
                    var spl = new WordCounterSpliterator(immuString.substring(currentPos, cutPos));
                    currentPos = cutPos;
                    return spl;
                }
            }
            return null;
        }

        @Override
        public long estimateSize() {
            return immuString.length() - currentPos;
        }

        @Override
        public int characteristics() {
            return ORDERED + SIZED + SUBSIZED + NONNULL + IMMUTABLE;
        }
    }
}
