package org.thor.base.utils.take.permission;

import org.thor.base.utils.take.model.InvokeParam;

/**
 * 授权管理回调
 */
public interface InvokeListener {
    PermissionManager.TPermissionType invoke(InvokeParam invokeParam);
}
