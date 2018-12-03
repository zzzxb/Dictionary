package com.mini.dictionary;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.mini.dictionary.panel.DictionaryLayout;
import com.mini.dictionary.panel.DictionaryRender;

public class Dictionary extends ApplicationAdapter {
	private DictionaryRender dictionaryRender;
	private DictionaryLayout dictionaryLayout;

	@Override
	/** 创建, 用于初始化*/
	public void create () {
	    dictionaryLayout = new DictionaryLayout();
		dictionaryRender = new DictionaryRender(dictionaryLayout);
	}

	@Override
	/** 渲染，用于显示图像*/
	public void render () {
		Gdx.gl.glClearColor(255, 255, 255, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		dictionaryRender.render();
	}

	@Override
	/** 释放资源*/
	public void dispose () {
		dictionaryRender.dispose();
	}
}
