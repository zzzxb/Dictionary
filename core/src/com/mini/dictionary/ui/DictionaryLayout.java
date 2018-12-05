package com.mini.dictionary.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class DictionaryLayout {
    private Stage stage;
    private SelectionMenu selectionMenu;

    public DictionaryLayout(){
        stage = new Stage(); // 创建舞台
        Gdx.input.setInputProcessor(stage); // 输入响应
        selectionMenu = new SelectionMenu(stage);
        init();
    }

    public void init() {
        selectionMenu.init();
    }

    public void render() {
        stage.act(); // 更新舞台逻辑
        stage.draw(); // 绘制舞台
        selectionMenu.event();
    }

    public void dispose() {
        if (stage != null)
            stage.dispose();
        selectionMenu.dispose();
    }
}
