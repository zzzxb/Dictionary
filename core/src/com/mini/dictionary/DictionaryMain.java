package com.mini.dictionary;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.mini.dictionary.ui.DictionaryLayout;
import com.mini.dictionary.ui.DictionaryRender;

public class DictionaryMain extends ApplicationAdapter {
	private DictionaryLayout dictionaryLayout;
	private DictionaryRender dictionaryRender;

	@Override
	/** 用于初始化*/
	public void create () {
		dictionaryLayout = new DictionaryLayout();
		dictionaryRender = new DictionaryRender(dictionaryLayout);
	}

	@Override
	/** 渲染图像*/
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 0xff/255.0f); // 设置背景色
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // 清屏
		dictionaryRender.render();
	}

	@Override
    /** 释放资源*/
	public void dispose () {
		dictionaryRender.dispose();
	}
}
