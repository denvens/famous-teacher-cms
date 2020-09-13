package com.qingclass.squirrel.cms.Service;

import com.qingclass.squirrel.cms.entity.cms.RequestInfo;
import com.qingclass.squirrel.cms.entity.cms.SquirrelPicturebook;
import com.qingclass.squirrel.cms.mapper.cms.SquirrelPicturebookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class SquirrelPicturebookService {

    @Autowired
    private SquirrelPicturebookMapper squirrelPicturebookMapper;

    public RequestInfo selectAll(SquirrelPicturebook squirrelPicturebook){
        RequestInfo info;
        info = new RequestInfo();
        Integer pageNo = squirrelPicturebook.getPageNo();
        Integer pageSize = squirrelPicturebook.getPageSize();

        pageNo = (pageNo-1)*pageSize;
        squirrelPicturebook.setPageNo(pageNo);
        List<SquirrelPicturebook> squirrelPicturebooks = squirrelPicturebookMapper.selectAll(squirrelPicturebook);
        info.setDataList(squirrelPicturebooks);
        return info;
    }
    public List<SquirrelPicturebook> selectAll(){
        List<SquirrelPicturebook> squirrelPicturebooks = squirrelPicturebookMapper.selectBase();


        for(int i = 0 ; i < squirrelPicturebooks.size() ; i ++){
            SquirrelPicturebook squirrelPicturebook = squirrelPicturebooks.get(i);
            for(int j = i+1 ; j < squirrelPicturebooks.size(); j ++){
                SquirrelPicturebook b1 = squirrelPicturebooks.get(j);
                if((squirrelPicturebook.getId() - b1.getId() != 0) ){

                }else{
                    squirrelPicturebook.setUsePart(squirrelPicturebook.getUsePart()+","+b1.getUsePart());
                    squirrelPicturebooks.remove(j);
                    j = j - 1;
                }
            }
        }

        return squirrelPicturebooks;
    }

    public List<SquirrelPicturebook> selectAll(Integer levelId){
        List<SquirrelPicturebook> squirrelPicturebooks = squirrelPicturebookMapper.selectBaseByLevelId(levelId);

        for(int i = 0 ; i < squirrelPicturebooks.size() ; i ++){
            SquirrelPicturebook squirrelPicturebook = squirrelPicturebooks.get(i);
            for(int j = i+1 ; j < squirrelPicturebooks.size(); j ++){
                SquirrelPicturebook b1 = squirrelPicturebooks.get(j);
                if((squirrelPicturebook.getId() - b1.getId() != 0) ){

                }else{
                    squirrelPicturebook.setUsePart(squirrelPicturebook.getUsePart()+","+b1.getUsePart());
                    squirrelPicturebooks.remove(j);
                    j = j - 1;
                }
            }

            String s = squirrelPicturebook.getUsePart();
            if(s != null){
                String[] split = s.split(",");
                if(split.length == squirrelPicturebook.getPart()){
                    squirrelPicturebooks.get(i).setNone(true);
                }
            }
        }



        return squirrelPicturebooks;
    }


    public RequestInfo selectByLevelId(SquirrelPicturebook squirrelPicturebook){
        RequestInfo info;
        info = new RequestInfo();
        Integer pageNo = squirrelPicturebook.getPageNo();
        Integer pageSize = squirrelPicturebook.getPageSize();

        pageNo = (pageNo-1)*pageSize;
        squirrelPicturebook.setPageNo(pageNo);
        List<SquirrelPicturebook> squirrelPicturebooks = squirrelPicturebookMapper.selectBy(squirrelPicturebook);
        info.setDataList(squirrelPicturebooks);
        return info;
    }

    public RequestInfo selectById(Integer id){
        RequestInfo info;
        info = new RequestInfo();

        SquirrelPicturebook squirrelPicturebooks = squirrelPicturebookMapper.selectById(id);
        info.setDataObject(squirrelPicturebooks);
        return info;
    }

    public RequestInfo insert(SquirrelPicturebook squirrelPicturebook){
        RequestInfo info;
        info = new RequestInfo();
        squirrelPicturebookMapper.insert(squirrelPicturebook);
        return info;
    }

    public RequestInfo update(SquirrelPicturebook squirrelPicturebook){
        RequestInfo info;
        info = new RequestInfo();
        squirrelPicturebookMapper.updateByPrimaryKey(squirrelPicturebook);
        return info;
    }

    public RequestInfo delete(Integer id){
        RequestInfo info;
        info = new RequestInfo();
        squirrelPicturebookMapper.deleteByPrimaryKey(id);
        return info;
    }

}
