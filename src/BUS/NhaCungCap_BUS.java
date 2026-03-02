package BUS;

import DAO.NhaCungCap_DAO;
import DTO.NhaCungCap_DTO;

import java.util.ArrayList;

public class NhaCungCap_BUS {
    NhaCungCap_DAO nccDAO = new NhaCungCap_DAO();
    public ArrayList<NhaCungCap_DTO> getAllNhaCungCap() {
        return nccDAO.getAllNhaCungCap();
    }
    public String addNCC(NhaCungCap_DTO ncc) {

        if (ncc.getMaNCC() == null || ncc.getMaNCC().trim().isEmpty()) {
            return "Mã nhà cung cấp không được rỗng";
        }

        if (ncc.getTenNCC() == null || ncc.getTenNCC().trim().isEmpty()) {
            return "Tên nhà cung cấp không được rỗng";
        }

        if (ncc.getDiaChi() == null || ncc.getDiaChi().trim().isEmpty()) {
            return "Địa chỉ không được rỗng";
        }

        if (ncc.getSoDT() == null || ncc.getSoDT().trim().isEmpty()) {
            return "Số điện thoại không được rỗng";
        }

        if (nccDAO.isDuplicateMaNCC(ncc.getMaNCC())) {
            return "Mã nhà cung cấp đã tồn tại";
        }

        if (!ncc.getSoDT().matches("\\d{10}")) {
            return "Số điện thoại phải gồm 10 chữ số";
        }


        // 4. Thêm vào DB
        boolean success = nccDAO.addNhaCungCap(ncc);

        if (success)
            return "Success";

        return "Thêm thất bại";
    }

    public boolean deleteNCC(String maNCC) {
        return  nccDAO.deleteNhaCungCap(maNCC);
    }

    public String updateNCC(NhaCungCap_DTO ncc) {
        if (ncc.getMaNCC() == null || ncc.getMaNCC().trim().isEmpty()) {
            return "Mã nhà cung cấp không được rỗng";
        }

        if (ncc.getTenNCC() == null || ncc.getTenNCC().trim().isEmpty()) {
            return "Tên nhà cung cấp không được rỗng";
        }

        if (ncc.getDiaChi() == null || ncc.getDiaChi().trim().isEmpty()) {
            return "Địa chỉ không được rỗng";
        }

        if (ncc.getSoDT() == null || ncc.getSoDT().trim().isEmpty()) {
            return "Số điện thoại không được rỗng";
        }

        if (!ncc.getSoDT().matches("\\d{10}")) {
            return "Số điện thoại phải gồm 10 chữ số";
        }

        boolean success = nccDAO.updateNhaCungCap(ncc);
        if(success) {
            return "Success";
        }
        return "Sửa không thành công";
    }

    public String getTenNCCByMaNCC(String maNCC) {
        return nccDAO.getTenNhaCungCapByMaNCC(maNCC);
    }

    public ArrayList<NhaCungCap_DTO> timNCCTheoTenNCC(String keyword) {
        return nccDAO.timKiemNCCTheoTen(keyword);
    }
}
