package Interface;

import data.ConvertDebt;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author zouyongchuan
 */
@Service
public class Request {
    private static Logger LOGGER = LoggerFactory.getLogger(Request.class);

    @Autowired
    private String url;
    @Autowired
    private String defaultUrl;
    @Autowired
    private Integer tdHrefPosition;
    @Autowired
    private Integer skipNumber;
    @Autowired
    private Integer timeOut;
    @Autowired
    private Integer backGroudWaitTime;

    @RequestMapping(value = "/data",method = RequestMethod.GET)
    @ResponseBody
    public Optional<Map<Integer, List<String>>> data(){
        try {
            ConvertDebt convertDebt = new ConvertDebt(timeOut, url, backGroudWaitTime, skipNumber, tdHrefPosition, defaultUrl);
            return convertDebt.get();
        }catch (Exception e){
            LOGGER.error(String.valueOf(e));
            return Optional.empty();
        }
    }
}
