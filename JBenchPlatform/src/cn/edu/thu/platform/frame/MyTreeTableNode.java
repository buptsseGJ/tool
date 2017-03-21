/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.edu.thu.platform.frame;

import java.util.Random;

/**
 *
 * @author vearn
 */
public class MyTreeTableNode {

    private String name;
    private String value1 = "";
    private String value2 = "";
    private String value3 = "";
    private String value4 = "";
    private String value5 = "";

    public MyTreeTableNode(String _name) {
        this.name = _name;
    }
    public MyTreeTableNode(String _name,String value1,String value2,String value3,String value4,String value5) {
        this.name = _name;
        this.value1 = value1;
        this.value2 = value2;
        this.value3 = value3;
        this.value4 = value4;
        this.value5 = value5;
    }
    /**
     * 重载Object的toString方法、决定checkboxtreetable中tree节点的显示内容
     */
    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public String getValue1() {
		return value1;
	}

	public void setValue1(String value1) {
		this.value1 = value1;
	}

	public String getValue2() {
		return value2;
	}

	public void setValue2(String value2) {
		this.value2 = value2;
	}

	public String getValue3() {
		return value3;
	}

	public void setValue3(String value3) {
		this.value3 = value3;
	}

	public String getValue4() {
		return value4;
	}

	public void setValue4(String value4) {
		this.value4 = value4;
	}

	public String getValue5() {
		return value5;
	}

	public void setValue5(String value5) {
		this.value5 = value5;
	}

}