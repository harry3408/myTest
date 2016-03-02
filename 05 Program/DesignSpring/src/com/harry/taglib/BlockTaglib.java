package com.harry.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.context.ApplicationContext;

import com.harry.taglib.block.BlockAbstract;
import com.harry.utils.SpringUtil;

public class BlockTaglib extends TagSupport {
    private static final long serialVersionUID = 7258117006212633380L;

    // 定义block的名字，值像product_cat等
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int doStartTag() throws JspException {
        return SKIP_BODY;
    }

    @Override
    public int doEndTag() throws JspException {
        // 这里是使用的spring框架，如果你不使用，那么你应该先解析那个block定义的xml文件
        ApplicationContext ctx = SpringUtil.getApplicationContext();
        // 取到相应的block对像，一看就知道要定义一个抽像的block，其它bolck继承它
        BlockAbstract block = (BlockAbstract) ctx.getBean(name);
        // 得到生成的Html片段
        String content = block.displayBlock(pageContext);
        // 然后在页面上输出
        JspWriter out = pageContext.getOut();
        try {
            out.println(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return EVAL_PAGE;
    }

    @Override
    public void release() {
        super.release();
    }
}
