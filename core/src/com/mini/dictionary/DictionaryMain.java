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
	/** 用于初始化1*/
	public void create () {
		dictionaryLayout = new DictionaryLayout();
		dictionaryRender = new DictionaryRender(dictionaryLayout);
	}

	@Override
	/** 渲染图像*/
	public void render () {
		Gdx.gl.glClearColor(255, 255, 255, 1); // 设置背景色
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // 清屏

		dictionaryRender.render();
	}

	@Override
    /** 释放资源*/
	public void dispose () {
	}
}
