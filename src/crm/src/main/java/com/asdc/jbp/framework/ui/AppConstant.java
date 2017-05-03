/*
 * Project Name : jbp-framework <br>
 * File Name : AppConstant.java <br>
 * Package Name : com.asdc.jbp.framework.ui <br>
 * Create Time : Apr 12, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 * Copyright © 2006, 2016, ASDC DAI. All rights reserved.
 */
package com.asdc.jbp.framework.ui;

/**
 * ClassName : AppConstant <br>
 * Description : constant of UI Layer <br>
 * Create Time : Apr 12, 2016 <br>
 * Create by : xiangyu_li@asdc.com.cn <br>
 *
 */
@SuppressWarnings("unused")
public interface AppConstant {

    /**
     * ClassName : CNS_SERVER <br>
     * Description : controller constant <br>
     * Create Time : Apr 12, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     */
    enum CNS_SERVER {
        /** For @Fields ACTION: action controller name */
        CONTROLLER,
        /** method name */
        METHOD;

        @Override
        public String toString() {
            return this.name().toLowerCase();
        }
    }

    /**
     * ClassName : CNS_WORKDTO <br>
     * Description : work dto constant <br>
     * Create Time : Apr 12, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     */
    enum CNS_WORKDTO {
        /** input stream flag of request */
        INPUTSTREAM,
        /** output stream flag of response */
        OUTPUTSTREAM;

        @Override
        public String toString() {
            return this.name().toLowerCase();
        }
    }

    /**
     * ClassName : CNS_REQUEST <br>
     * Description : request flag <br>
     * Create Time : Apr 12, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     */
    enum CNS_REQUEST {
        LET_ME_CTRL_THE_STREAM,
        /** SUCCESS flag */
        SUCCESS,
        /** error code */
        ERR_CODE,
        /** error level warning or error */
        ERR_LEVEL,
        /** error message */
        ERR_MESSAGE;

        public final static String CNS_ERROR = "error";
        public final static String CNS_WARNING = "warning";

        @Override
        public String toString() {
            if (this.equals(SUCCESS)) {
                return "success";
            } else if (this.equals(ERR_MESSAGE)) {
                return "errMsg";
            } else if (this.equals(ERR_CODE)) {
                return "errCode";
            } else if (this.equals(ERR_LEVEL)) {
                return "errLevel";
            } else if (this.equals(LET_ME_CTRL_THE_STREAM)) {
                return "selfControledStream";
            }
            return this.name().toLowerCase();
        }
    }
    /**
     * ClassName : CNS_FILE <br>
     * Description : file constant <br>
     * Create Time : Apr 12, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     */
    enum CNS_FILE {
        /** file request key */
        CNS_FILE_KEY,
        /** file result success flag key */
        SUCCESS,
        /** file result key and the result should be {id:"id", name:"name", url:"url"} */
        CNS_FILE_RESULT;

        @Override
        public String toString() {
            return this.name().toLowerCase();
        }
    }

    /**
     * ClassName : CNS_LIST_REQUEST <br>
     * Description : list request cns, such as grid, tree, combo <br>
     * Create Time : Apr 12, 2016 <br>
     * Create by : xiangyu_li@asdc.com.cn <br>
     *
     */
    enum CNS_LIST_REQUEST {
        /** key of result in response */
        RESULT,
        /** key of total in response */
        TOTAL,
        /** key of page start in request */
        START,
        /** key of page limit in request */
        LIMIT;

        @Override
        public String toString() {
            return this.name().toLowerCase();
        }
    }
}
