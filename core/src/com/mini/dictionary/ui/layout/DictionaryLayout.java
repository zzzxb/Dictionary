package com.mini.dictionary.ui.layout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class DictionaryLayout {
    private Stage stage;
    private OptionMenu selectionMenu;

    public DictionaryLayout(){
        stage = new Stage(); // 创建舞台
        Gdx.input.setInputProcessor(stage); // 输入响应
        selectionMenu = new OptionMenu(stage);
        init();
    }

    public void init() {
        selectionMenu.init();
    }

    public void render() {
        stage.act(); // 更新舞台逻辑
        stage.draw(); // 绘制舞台
        selectionMenu.buttonEvent(); // 选择菜单
    }

    public void dispose() {
        stage.dispose();
    }
}
