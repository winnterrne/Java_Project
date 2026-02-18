package BUS;
import DAO.*;
import DTO.*;

import javax.swing.*;
import java.util.ArrayList;

public class TaiKhoan_BUS {
    private TaiKhoan_DAO taikhoan;
    public TaiKhoan_BUS () {
        taikhoan = new TaiKhoan_DAO();
    }
    public ArrayList<TaiKhoan_DTO> getALL() {
        return taikhoan.getALL();
    }
    public boolean addTaiKhoan(TaiKhoan_DTO taikhoandto) {
        if(taikhoan.isUsernameExist(taikhoandto.getMaTK())) {
            return false;
        }
        if(taikhoan.addTaiKhoan(taikhoandto)) {
            return true;
        }
        return false;
    }
    public boolean deleteTaiKhoan(String matkhau) {
        if(matkhau == null || matkhau.isEmpty()) {
            return false;
        }
        if(!taikhoan.isUsernameExist(matkhau)) {
            return false;
        }
        if(taikhoan.deleteTaiKhoan(matkhau)) {
            return true;
        }
        return false;
    }
    public boolean updateTaiKhoan(TaiKhoan_DTO taikhoandto) {
        if(taikhoandto.getTenDangNhap()== null || taikhoandto.getTenDangNhap() == null) {
            return false;
        }
        return taikhoan.updateTaikhoan(taikhoandto);
    }
    public boolean updatePassWord(String matkhaucu, String mataikhoan, String matkhaumoi) {
        TaiKhoan_DTO taikhoandto = new TaiKhoan_DTO();
        if(!taikhoandto.getMaTK().equals(mataikhoan)) {
            return false;
        }
        if(!taikhoandto.getPassWord().equals(matkhaucu)) {
            return false;
        }
        if(matkhaumoi.length() < 6) {
            return true;
        }
        return taikhoan.updatePassword(mataikhoan,matkhaumoi);
    }
}
