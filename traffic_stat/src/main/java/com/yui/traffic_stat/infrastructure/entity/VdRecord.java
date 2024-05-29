package com.yui.traffic_stat.infrastructure.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName vd_record
 */
@TableName(value ="vd_record")
@Data
public class VdRecord implements Serializable {
    /**
     * 唯一主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 创建者
     */
    private String creator;

    /**
     * 修改者
     */
    private String modifier;

    /**
     * 创建时间
     */
    private Date created;

    /**
     * 修改时间
     */
    private Date modified;

    /**
     * 是否删除
     */
    private Integer isDeleted;

    /**
     * 检测设备的id
     */
    private Long deviceId;

    /**
     * 车辆类型（与检测系统类型对应）
     */
    private Integer vehicleType;

    /**
     * 车辆in数量
     */
    private Integer vehicleInNumber;

    /**
     * 车辆out数量
     */
    private Integer vehicleOutNumber;

    /**
     * 统计的时间粒度（秒）
     */
    private Integer granularity;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        VdRecord other = (VdRecord) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
            && (this.getModifier() == null ? other.getModifier() == null : this.getModifier().equals(other.getModifier()))
            && (this.getCreated() == null ? other.getCreated() == null : this.getCreated().equals(other.getCreated()))
            && (this.getModified() == null ? other.getModified() == null : this.getModified().equals(other.getModified()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
            && (this.getDeviceId() == null ? other.getDeviceId() == null : this.getDeviceId().equals(other.getDeviceId()))
            && (this.getVehicleType() == null ? other.getVehicleType() == null : this.getVehicleType().equals(other.getVehicleType()))
            && (this.getVehicleInNumber() == null ? other.getVehicleInNumber() == null : this.getVehicleInNumber().equals(other.getVehicleInNumber()))
            && (this.getVehicleOutNumber() == null ? other.getVehicleOutNumber() == null : this.getVehicleOutNumber().equals(other.getVehicleOutNumber()))
            && (this.getGranularity() == null ? other.getGranularity() == null : this.getGranularity().equals(other.getGranularity()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getModifier() == null) ? 0 : getModifier().hashCode());
        result = prime * result + ((getCreated() == null) ? 0 : getCreated().hashCode());
        result = prime * result + ((getModified() == null) ? 0 : getModified().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        result = prime * result + ((getDeviceId() == null) ? 0 : getDeviceId().hashCode());
        result = prime * result + ((getVehicleType() == null) ? 0 : getVehicleType().hashCode());
        result = prime * result + ((getVehicleInNumber() == null) ? 0 : getVehicleInNumber().hashCode());
        result = prime * result + ((getVehicleOutNumber() == null) ? 0 : getVehicleOutNumber().hashCode());
        result = prime * result + ((getGranularity() == null) ? 0 : getGranularity().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", creator=").append(creator);
        sb.append(", modifier=").append(modifier);
        sb.append(", created=").append(created);
        sb.append(", modified=").append(modified);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", deviceId=").append(deviceId);
        sb.append(", vehicleType=").append(vehicleType);
        sb.append(", vehicleInNumber=").append(vehicleInNumber);
        sb.append(", vehicleOutNumber=").append(vehicleOutNumber);
        sb.append(", granularity=").append(granularity);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}