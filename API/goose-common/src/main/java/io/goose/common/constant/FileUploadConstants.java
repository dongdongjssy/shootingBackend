package io.goose.common.constant;

public interface FileUploadConstants {
	
	
    public enum UploadCategory
    {
        /**
                 * 文件上传到本地
         */
        LOCAL(1),
        /**
         * 文件上传到阿里云OSS
         */
        AliyunOss(2),
    	
    	 /**
         * 文件上传到亚马逊AWS
         */
        AWS(3);

        private int value;

        private UploadCategory(Integer value)
        {
            this.value = value;
        }

        public Integer getValue()
        {
            return value;
        }
    }

}
