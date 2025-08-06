/**
 * Copyright (c) 2018-2099, Chill Zhuang 庄骞 (bladejava@qq.com).
 * <p>
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/lgpl.html
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.weakwater.framework.core.api;

import com.weakwater.framework.core.constant.AppConstant;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.lang.Nullable;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.Optional;

/**
 * 统一API响应结果封装
 *
 * @author Chill
 */
@Getter
@Setter
@ToString
@Schema(description = "返回信息")
@NoArgsConstructor
public class ApiResult<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "状态码")
    private int code;
    @Schema(description = "是否成功")
    private boolean success;
    @Schema(description = "承载数据")
    private T data;
    @Schema(description = "返回消息")
    private String msg;

    private ApiResult(IResultCode resultCode) {
        this(resultCode, null, resultCode.getMessage());
    }

    private ApiResult(IResultCode resultCode, String msg) {
        this(resultCode, null, msg);
    }

    private ApiResult(IResultCode resultCode, T data) {
        this(resultCode, data, resultCode.getMessage());
    }

    private ApiResult(IResultCode resultCode, T data, String msg) {
        this(resultCode.getCode(), data, msg);
    }

    private ApiResult(int code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
        this.success = ResultCode.SUCCESS.code == code;
    }

    /**
     * 判断返回是否为成功
     *
     * @param result Result
     * @return 是否成功
     */
    public static boolean isSuccess(@Nullable ApiResult<?> result) {
        return Optional.ofNullable(result)
                .map(x -> ObjectUtils.nullSafeEquals(ResultCode.SUCCESS.code, x.code))
                .orElse(Boolean.FALSE);
    }

    /**
     * 判断返回是否为成功
     *
     * @param result Result
     * @return 是否成功
     */
    public static boolean isNotSuccess(@Nullable ApiResult<?> result) {
        return !ApiResult.isSuccess(result);
    }

    /**
     * 返回R
     *
     * @param data 数据
     * @param <T>  T 泛型标记
     * @return R
     */
    public static <T> ApiResult<T> data(T data) {
        return data(data, AppConstant.DEFAULT_SUCCESS_MESSAGE);
    }

    /**
     * 返回R
     *
     * @param data 数据
     * @param msg  消息
     * @param <T>  T 泛型标记
     * @return R
     */
    public static <T> ApiResult<T> data(T data, String msg) {
        return data(HttpServletResponse.SC_OK, data, msg);
    }

    /**
     * 返回R
     *
     * @param code 状态码
     * @param data 数据
     * @param msg  消息
     * @param <T>  T 泛型标记
     * @return R
     */
    public static <T> ApiResult<T> data(int code, T data, String msg) {
        return new ApiResult<>(code, data, data == null ? AppConstant.DEFAULT_NULL_MESSAGE : msg);
    }

    /**
     * 返回成功
     *
     * @param <T> 泛型标记
     * @return Result
     */
    public static <T> ApiResult<T> success() {
        return new ApiResult<>(ResultCode.SUCCESS);
    }

    /**
     * 返回R
     *
     * @param msg 消息
     * @param <T> T 泛型标记
     * @return R
     */
    public static <T> ApiResult<T> success(String msg) {
        return new ApiResult<>(ResultCode.SUCCESS, msg);
    }

    /**
     * 返回R
     *
     * @param resultCode 业务代码
     * @param <T>        T 泛型标记
     * @return R
     */
    public static <T> ApiResult<T> success(IResultCode resultCode) {
        return new ApiResult<>(resultCode);
    }

    /**
     * 返回R
     *
     * @param resultCode 业务代码
     * @param msg        消息
     * @param <T>        T 泛型标记
     * @return R
     */
    public static <T> ApiResult<T> success(IResultCode resultCode, String msg) {
        return new ApiResult<>(resultCode, msg);
    }

    /**
     * 返回R
     *
     * @param msg 消息
     * @param <T> T 泛型标记
     * @return R
     */
    public static <T> ApiResult<T> fail(String msg) {
        return new ApiResult<>(ResultCode.FAILURE, msg);
    }


    /**
     * 返回R
     *
     * @param code 状态码
     * @param msg  消息
     * @param <T>  T 泛型标记
     * @return R
     */
    public static <T> ApiResult<T> fail(int code, String msg) {
        return new ApiResult<>(code, null, msg);
    }

    /**
     * 返回R
     *
     * @param resultCode 业务代码
     * @param <T>        T 泛型标记
     * @return R
     */
    public static <T> ApiResult<T> fail(IResultCode resultCode) {
        return new ApiResult<>(resultCode);
    }

    /**
     * 返回R
     *
     * @param resultCode 业务代码
     * @param msg        消息
     * @param <T>        T 泛型标记
     * @return R
     */
    public static <T> ApiResult<T> fail(IResultCode resultCode, String msg) {
        return new ApiResult<>(resultCode, msg);
    }

    /**
     * 返回R
     *
     * @param flag 成功状态
     * @param <T>  泛型
     * @return R
     */
    public static <T> ApiResult<T> status(boolean flag) {
        return flag ? success(AppConstant.DEFAULT_SUCCESS_MESSAGE) : fail(AppConstant.DEFAULT_FAILURE_MESSAGE);
    }

    /**
     * 根据状态返回成功或者失败
     *
     * @param status 状态
     * @param msg    异常msg
     * @param <T>    泛型标记
     * @return Result
     */
    public static <T> ApiResult<T> status(boolean status, String msg) {
        return status ? ApiResult.success() : ApiResult.fail(msg);
    }

}
