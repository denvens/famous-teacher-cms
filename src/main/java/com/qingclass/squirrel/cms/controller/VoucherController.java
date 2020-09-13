package com.qingclass.squirrel.cms.controller;

import com.qingclass.squirrel.cms.entity.cms.CmsAdmin;
import com.qingclass.squirrel.cms.entity.user.Voucher;
import com.qingclass.squirrel.cms.entity.user.VoucherOperRecord;
import com.qingclass.squirrel.cms.mapper.user.VoucherMapper;
import com.qingclass.squirrel.cms.utils.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/voucher")
public class VoucherController {

    @Autowired
    private VoucherMapper voucherMapper;

    @ResponseBody
    @RequestMapping(value = "list", method = RequestMethod.POST)
    public Map<String,Object> list(@RequestParam(value="pageNo",required = false)Integer pageNo, @RequestParam(value="pageSize",required = false)Integer pageSize,
                                   @RequestParam(value="userParam",required = false)String userParam, @RequestParam(value = "createdAt",required = false)String createdAt,
                                   @RequestParam(value = "useTime",required = false)String useTime,@RequestParam(value = "status",required = false)Integer status,
                                   @RequestParam(value = "levelId",required = false)Integer levelId) throws ParseException {

        pageNo = (pageNo - 1) * pageSize;
        Voucher voucher = new Voucher();
        voucher.setPageNo(pageNo);
        voucher.setPageSize(pageSize);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(userParam != null && !userParam.equals("")){
            voucher.setUserParams(userParam);
        }
        if(createdAt != null && !createdAt.equals("")){
            voucher.setCreatedAt(createdAt);
            String[] s = createdAt.split(",");
            voucher.setCreatedAtA(sdf.parse(s[0]));
            voucher.setCreatedAtB(sdf.parse(s[1]));
        }
        if(useTime !=null && !useTime.equals("")){
            voucher.setUseTime(useTime);
            String[] s = useTime.split(",");
            voucher.setUseTimeA(sdf.parse(s[0]));
            voucher.setUseTimeB(sdf.parse(s[1]));
        }
        if(status != null && !status.equals("")){
            voucher.setStatus(status);
        }
        if(levelId != null){
            voucher.setLevelId(levelId);
        }

        List<Voucher> vouchers = voucherMapper.selectByParams(voucher);
        for(Voucher v : vouchers){
            v.setCreatedAt(sdf.format(v.getCreatedAtA()));
            if(v.getUseTimeA() !=null && !v.getUseTimeA().equals("")){
                v.setUseTime(sdf.format(v.getUseTimeA()));
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("list",vouchers);
        map.put("total",voucherMapper.selectByParamsCount(voucher));

        return Tools.s(map);
    }

    @ResponseBody
    @RequestMapping(value = "edit-status", method = RequestMethod.POST)
    public Map<String,Object> editStatus(HttpServletRequest req, @RequestParam(value="id",required = false)Integer id, @RequestParam(value="isOpen",required = false)Integer isOpen){
        Voucher voucher = new Voucher();
        voucher.setId(id);
        voucher.setIsOpen(isOpen);
        voucherMapper.updateByPrimaryKey(voucher);

        CmsAdmin admin = (CmsAdmin) req.getSession().getAttribute(CmsAdmin.SESSION_SQUIRREL_ADMIN_KEY);
        VoucherOperRecord voucherOperRecord = new VoucherOperRecord();
        voucherOperRecord.setType(isOpen);
        voucherOperRecord.setVoucherId(id);
        voucherOperRecord.setAdminId(admin.getId());

        voucherMapper.insertRecord(voucherOperRecord);
        if(isOpen == 0){
            Voucher voucher1 = voucherMapper.selectByPrimaryKey(id);
            voucherMapper.insertProhibit(voucher1.getLevelId(),voucher1.getSquirrelUserId());
        }

        return Tools.s();
    }

    @ResponseBody
    @RequestMapping(value = "record-list", method = RequestMethod.POST)
    public Map<String,Object> recordList(@RequestParam(value="pageNo",required = false)Integer pageNo, @RequestParam(value="pageSize",required = false)Integer pageSize,
                                         @RequestParam(value="userParam",required = false)String userParam, @RequestParam(value = "updatedAt",required = false)String updatedAt,
                                         @RequestParam(value = "levelId",required = false)Integer levelId) throws ParseException {

        pageNo = (pageNo - 1) * pageSize;
        VoucherOperRecord voucherOperRecord = new VoucherOperRecord();
        voucherOperRecord.setPageNo(pageNo);
        voucherOperRecord.setPageSize(pageSize);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(levelId != null && !levelId.equals("")){
            voucherOperRecord.setLevelId(levelId);
        }
        if(userParam != null && !userParam.equals("")){
            voucherOperRecord.setUserParams(userParam);
        }
        if(updatedAt != null && !updatedAt.equals("")){
            voucherOperRecord.setUpdatedAt(updatedAt);
            String[] s = updatedAt.split(",");
            voucherOperRecord.setUpdateAtA(sdf.parse(s[0]));
            voucherOperRecord.setUpdateAtB(sdf.parse(s[1]));
        }
        List<VoucherOperRecord> voucherOperRecords = voucherMapper.selectRecord(voucherOperRecord);
        for(VoucherOperRecord vr : voucherOperRecords){
            if(vr.getCreatedAtA() != null){
                vr.setCreatedAt(sdf.format(vr.getCreatedAtA()));
            }
            if(vr.getUpdateAtA() != null){
                vr.setUpdatedAt(sdf.format(vr.getUpdateAtA()));
            }

        }

        Map<String,Object> map = new HashMap<>();
        map.put("list",voucherOperRecords);
        map.put("total",voucherMapper.selectRecordCount(voucherOperRecord));
        return Tools.s(map);
    }
}
