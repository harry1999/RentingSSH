package com.harry.data;


import java.io.Serializable;
import java.util.List;

public class Data <T, E> implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	private E e;

    private List <T> list;
    
    
    public E getE() {
		return e;
	}

	public void setE(E e) {
		this.e = e;
	}

	public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
    

	@Override
	public String toString() {
		return "Data [e=" + e + ", list=" + list + "]";
	}

}
