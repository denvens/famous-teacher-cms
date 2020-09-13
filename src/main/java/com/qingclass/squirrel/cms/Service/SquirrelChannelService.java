package com.qingclass.squirrel.cms.Service;


import com.google.zxing.WriterException;
import com.qingclass.squirrel.cms.constant.Global;
import com.qingclass.squirrel.cms.entity.cms.SquirrelChannel;
import com.qingclass.squirrel.cms.mapper.cms.SquirrelChannelMapper;
import com.qingclass.squirrel.cms.utils.OssUtil;
import com.qingclass.squirrel.cms.utils.QRCodeFactory;
import com.qingclass.squirrel.cms.utils.WxUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class SquirrelChannelService {

	private Logger logger = LoggerFactory.getLogger(SquirrelChannelService.class);

	@Resource
	private StringRedisTemplate stringRedisTemplate;
	@Autowired
	SquirrelChannelMapper squirrelChannelMapper;


	public int insert(SquirrelChannel squirrelChannel){
		String accessToken = stringRedisTemplate.opsForValue().get("msyb_access_token")+"";
		WxUtil wxUtil = new WxUtil();
		Map foreverStrQr = wxUtil.createForeverStrQr(squirrelChannel.getScene(), accessToken);
		squirrelChannel.setTicket(foreverStrQr.get("ticket").toString());
		if(foreverStrQr.get("expire_seconds") != null){
			squirrelChannel.setExpireSeconds(foreverStrQr.get("expire_seconds").toString());
		}

		try {
			File file = new QRCodeFactory().CreatQrImage(foreverStrQr.get("url").toString(), "png", "channelFile", "**");
			OssUtil ossUtil = new OssUtil();
			String site = ossUtil.executeUpLoad(new Global().getOssQrPath(), file, ".png");
			squirrelChannel.setSite(site);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (WriterException e) {
			e.printStackTrace();
		}

		squirrelChannel.setUrl(foreverStrQr.get("url").toString());
		try{
			squirrelChannelMapper.insert(squirrelChannel);
		}catch (Exception e){
			logger.error("Channel insertion failure...");
		}

		return 0;
	}

	public List<SquirrelChannel> selectAll(SquirrelChannel squirrelChannel){
		squirrelChannel.setPageNo((squirrelChannel.getPageNo()-1)*squirrelChannel.getPageSize());

		List<SquirrelChannel> squirrelChannels = squirrelChannelMapper.selectAll(squirrelChannel);
		return squirrelChannels;
	}

}
