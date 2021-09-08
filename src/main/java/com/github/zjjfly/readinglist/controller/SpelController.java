package com.github.zjjfly.readinglist.controller;

import com.github.zjjfly.readinglist.model.Inventor;
import com.github.zjjfly.readinglist.model.PlaceOfBirth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.GregorianCalendar;

/**
 * @author zjjfly[https://github.com/zjjfly] on 2020/4/16
 */
@RestController
@RequestMapping("/spel")
@Slf4j
public class SpelController {

    @GetMapping
    public void execute() throws NoSuchMethodException {
        // Create and set a calendar
        GregorianCalendar c = new GregorianCalendar();
        c.set(1990, 1, 4);
        // The constructor arguments are name, birthday, and nationality.
        Inventor tesla = new Inventor("Zi JunJie", c.getTime(), "China");
        tesla.setPlaceOfBirth(new PlaceOfBirth("Suzhou", "China"));
        ExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext context = new StandardEvaluationContext();
        //注册静态函数
        context.registerFunction("sum", SpelController.class.getDeclaredMethod("sha256", String.class));
        context.setVariable("a", 1);
        context.setVariable("b", 2);
        log.info("#sha(#a)" + parser.parseExpression("#sum(#a)").getValue(context, String.class));
    }

    public static String sha256(String s) {
        return s;
    }
}
