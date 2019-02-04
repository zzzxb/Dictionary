package com.mini.dictionary.ui.layout.page;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.mini.dictionary.ui.button.ButtonFramework;
import com.mini.dictionary.ui.layout.page.dao.OptionPageDao;
import com.mini.dictionary.util.LoadFile;

import static com.mini.dictionary.util.MenuInformation.BUTTONTWOPATH;

public class OptionSettingPage implements OptionPageDao {
    private Stage stage;

    private Label label;
    private Label label2;
    private ImageTextButton bookButton;
    private String[] book= {"单词本","四  级","升  本"};
    private int count = 0;

    public OptionSettingPage(Stage stage) {
        this.stage = stage;
        init();
    }

    @Override
    public void init() {
        label = new Label("每日背单词数",new Label.LabelStyle(LoadFile.getFont18(),null));
        label2 = new Label("单词本",new Label.LabelStyle(LoadFile.getFont18(),null));
        label.setPosition(200,500);
        label2.setPosition(200,450);
        backButton();
    }

    // 翻页
    public void backButton() {
        ButtonFramework buttonFramework= new ButtonFramework();
        buttonFramework.buttonMessage.setText(book[count]);
        buttonFramework.buttonMessage.setTexturePath (BUTTONTWOPATH[0],BUTTONTWOPATH[1],null);
        buttonFramework.buttonMessage.setFontFilePath("font/font18.fnt", "font/font18.png");
        buttonFramework.buttonMessage.setAxis(600, 450);
        bookButton = buttonFramework.createButton();
        stage.addActor(bookButton);
    }

    @Override
    public void addToStage() {
        stage.addActor(label);
        stage.addActor(label2);
        stage.addActor(bookButton);
        stage.addActor(LoadFile.getNumBox());
    }

    @Override
    public void showMessage() {
        int n = 0;
        try{
            if (LoadFile.getNumBox().getText().replace(" ","").equals("")){
                LoadFile.getNumBox().setText("");
            }else if ((n = Integer.parseInt(LoadFile.getNumBox().getText())) * 1 > 0) {
                LoadFile.setCount(n);
                LoadFile.getNumBox().setText(LoadFile.getCount() + "");
            }
        }catch (Exception e) {
            LoadFile.getNumBox().setText(LoadFile.getCount() + "");
        }
        if (bookButton.isChecked()) {
            bookButton.setChecked(false);
            count = ++count > (book.length - 1) ? 0 :count++;
            bookButton.setText(book[count]);
        }
    }
}
