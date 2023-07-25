

package wg.application.validator.group;

import javax.validation.GroupSequence;

/**
 * 定义校验顺序，如果AddGroup组失败，则UpdateGroup组不会再校验
 *
 * @author Seven ME info@7-me.net
 * @since 1.0.0
 */
@GroupSequence({AddGroup.class, UpdateGroup.class})
public interface Group {

}
