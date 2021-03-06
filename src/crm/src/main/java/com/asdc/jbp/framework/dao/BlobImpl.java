/*
 * Project Name jbp-framework
 * File Name BlobTool.java
 * Package Name com.asdc.jbp.framework.dao
 * Create Time 2016年5月26日
 * Create by yuruixin/ruixin_yu@asdc.com.cn <br>
 * Copyright © 2006, 2016, ASDC DAI. All rights reserved.
 */
package com.asdc.jbp.framework.dao;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;


/**
 * ClassName: BlobImpl.java <br>
 * Description: 将byte[]变量转换为Blob类型<br>
 * Create by: yuruixin/ruixin_yu@asdc.com.cn <br>
 * Create Time: 2016年5月26日<br>
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class BlobImpl implements Blob {


    private byte[] _bytes = new byte[0];

    private int _length = 0;

    /**
     * 构造函数，以byte[]构建blob
     *
     */
    public BlobImpl(byte[] bytes) {
        init(bytes);
    }

    /**
     * 构造函数，以blob重新构建blob
     *
     */
    public BlobImpl(Blob blob) {
        init(blobToBytes(blob));
    }

    /**
     * 初始化byte[]
     *
     */
    private void init(byte[] bytes) {
        _bytes = bytes;
        _length = _bytes.length;
    }

    /**
     * 将blob转为byte[]
     *
     */
    public static byte[] blobToBytes(Blob blob) {
        BufferedInputStream is = null;
        try {
            is = new BufferedInputStream(blob.getBinaryStream());
            byte[] bytes = new byte[(int) blob.length()];
            int len = bytes.length;
            int offset = 0, read;
            while (offset < len
                    && (read = is.read(bytes, offset, len - offset)) >= 0) {
                offset += read;
            }
            return bytes;
        } catch (Exception e) {
            return null;
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                //noinspection UnusedAssignment
                is = null;
            } catch (IOException e) {
                // ignore
            }

        }
    }

    /**
     * 获得blob中数据实际长度
     */
    public long length() throws SQLException {
        return _bytes.length;
    }

    /**
     * 返回指定长度的byte[]
     */
    public byte[] getBytes(long pos, int len) throws SQLException {
        if (pos == 0 && len == length())
            return _bytes;
        try {
            byte[] newbytes = new byte[len];
            System.arraycopy(_bytes, (int) pos, newbytes, 0, len);
            return newbytes;
        } catch (Exception e) {
            throw new SQLException("Inoperable scope of this array");
        }
    }

    /**
     * 返回InputStream
     */
    public InputStream getBinaryStream() throws SQLException {
        return new ByteArrayInputStream(_bytes);
    }

    /**
     * 获取此byte[]中start的字节位置
     */
    public long position(byte[] pattern, long start) throws SQLException {
        start--;
        if (start < 0) {
            throw new SQLException("start < 0");
        }
        if (start >= _length) {
            throw new SQLException("start >= max length");
        }
        if (pattern == null) {
            throw new SQLException("pattern == null");
        }
        if (pattern.length == 0 || _length == 0 || pattern.length > _length) {
            return -1;
        }
        int limit = _length - pattern.length;
        for (int i = (int) start; i <= limit; i++) {
            int p;
            for (p = 0; p < pattern.length && _bytes[i + p] == pattern[p]; p++) {
                if (p == pattern.length - 1) {
                    return i + 1;
                }
            }
        }
        return -1;
    }

    /**
     * 获取指定的blob中start的字节位置
     */
    public long position(Blob pattern, long start) throws SQLException {
        return position(blobToBytes(pattern), start);
    }

    /**
     * 不支持操作异常抛出
     *
     */
    void nonsupport() {
        throw new UnsupportedOperationException("This method is not supported！");
    }

    /**
     * 释放Blob对象资源
     */
    public void free() throws SQLException {
        _bytes = new byte[0];
        _length = 0;
    }

    /**
     * 返回指定长度部分的InputStream，并返回InputStream
     */
    public InputStream getBinaryStream(long pos, long len) throws SQLException {
        return new ByteArrayInputStream(getBytes(pos, (int) len));
    }

    /**
     * 以指定指定长度将二进制流写入OutputStream，并返回OutputStream
     */
    public OutputStream setBinaryStream(long pos) throws SQLException {
        // 暂不支持
        nonsupport();
        pos--;
        if (pos < 0) {
            throw new SQLException("pos < 0");
        }
        if (pos > _length) {
            throw new SQLException("pos > length");
        }
        // 将byte[]转为ByteArrayInputStream
        ByteArrayInputStream inputStream = new ByteArrayInputStream(_bytes);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        byte[] bytes;
        try {
            bytes = new byte[inputStream.available()];
            int read;
            while ((read = inputStream.read(bytes)) >= 0) {
                os.write(bytes, 0, read);
            }

        } catch (IOException e) {
            e.getStackTrace();
        }

        // 返回OutputStream
        return os;
    }

    /**
     * 设定byte[]
     */
    private int setBytes(long pos, byte[] bytes, int offset, int size, @SuppressWarnings("SameParameterValue") boolean copy) throws SQLException {
        // 暂不支持
        nonsupport();
        pos--;
        if (pos < 0) {
            throw new SQLException("pos < 0");
        }
        if (pos > _length) {
            throw new SQLException("pos > max length");
        }
        if (bytes == null) {
            throw new SQLException("bytes == null");
        }
        if (offset < 0 || offset > bytes.length) {
            throw new SQLException("offset < 0 || offset > bytes.length");
        }
        if (size < 0 || pos + size > (long) Integer.MAX_VALUE
                || offset + size > bytes.length) {
            throw new SQLException();
        }
        // 当copy数据时
        if (copy) {
            _bytes = new byte[size];
            System.arraycopy(bytes, offset, _bytes, 0, size);
        } else { // 否则直接替换对象
            _bytes = bytes;
        }
        return _bytes.length;
    }

    /**
     * 设定指定开始位置byte[]
     */
    public int setBytes(long pos, byte[] bytes) throws SQLException {
        // 暂不支持
        nonsupport();
        return setBytes(pos, bytes, 0, bytes.length, true);
    }

    /**
     * 设定byte[]
     */
    public int setBytes(long pos, byte[] bytes, int offset, int len)
            throws SQLException {
        // 暂不支持
        nonsupport();
        return setBytes(pos, bytes, offset, len, true);
    }

    /**
     * 截取相应部分数据
     */
    public void truncate(long len) throws SQLException {
        if (len < 0) {
            throw new SQLException("len < 0");
        }
        if (len > _length) {
            throw new SQLException("len > max length");
        }
        _length = (int) len;
    }
}