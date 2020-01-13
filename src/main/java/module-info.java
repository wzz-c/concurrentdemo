import com.sankuai.wangzizhou.demo.DemoService;
import com.sankuai.wangzizhou.demo.DemoServiceImpl;

/**
 * Desc:
 * Author:wangzizhou@meituan.com
 * Date: 2019/10/18
 * Time: 下午7:13
 */
module concurrentdemo {
    requires com.fasterxml.jackson.databind;
    requires jackson.annotations;
    requires jdk.unsupported;
    uses com.sankuai.wangzizhou.demo.DemoService;
    opens com.sankuai.wangzizhou.demo to com.fasterxml.jackson.databind;
    provides DemoService with DemoServiceImpl;
}