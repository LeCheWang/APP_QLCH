package com.tranthimai.qlch.TranMai_Adapter;

import com.tranthimai.qlch.TranMai_Model.TranMai_Khach;

public interface TranMai_IOnClickKhach {
    void iOnClickSua(TranMai_Khach khach, int pos);
    void iOnClickXoa(TranMai_Khach khach, int pos);
}
