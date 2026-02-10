package DAO;

import java.util.ArrayList;

public interface DAO_Interface<T> {
    public void them(T object);
    public void xoa(T object); //chi thay the chi so hien dien (available) thanh 0
    public ArrayList<T> xem(T object);
    public ArrayList<T> sua(T object1, T object2); //thay the object cu bang object moi
}
