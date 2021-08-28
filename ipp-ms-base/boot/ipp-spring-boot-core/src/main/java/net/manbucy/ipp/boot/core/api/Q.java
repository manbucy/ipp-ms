package net.manbucy.ipp.boot.core.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 统一数据请求体
 *
 * @author ManBu
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Q<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private String requestNo;
    private String clientVersion;
    private T data;
}
