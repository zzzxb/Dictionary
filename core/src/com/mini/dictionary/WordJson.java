package com.mini.dictionary;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;

public class WordJson extends ApplicationAdapter {

        public String init(String str,int num, int count) {
            Json json = new Json();
            json.setElementType(Character.class, "words", Item.class);
            Character character = json.fromJson(Character.class, Gdx.files.internal("character.json"));
            if (num == 1) // 查找单词
                return character.toExplain(str);
            else if (num == 2)
                return character.toEnCn(count);
            else // 查找长度
                return character.toLength();
        }

        public static class Item {  // 对象实体
            private String word;
            private String explain;

            public String toWord() {
                return word;
            }
            public String toExplain() {return explain;}
        }

        public static class Character { // 输出信息
            public Array<Item> words = new Array<Item>();

            public String toExplain(String str) {
                String string = new String();
                for (Item item : words) {
                    if (str.equals(item.toWord()))
                        string = item.toExplain() + "\n";
                }
                return string;
            }

            public String toLength() {
                int count = 0;
                for (Item item : words)
                    count++;
                return String.valueOf(count);
            }

            public String toEnCn(int count) {
                for (Item item : words)
                    if (count == 0)
                        return item.toWord() + "\n\n" + item.toExplain();
                    else
                        count --;
                    return "";
            }
        }
}
