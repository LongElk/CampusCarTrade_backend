package org.example.campuscartrade.pojo.DTO;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.List;

public class VehicleDTO {
    // 卖家 ID（必传，加在原数据中）
    @NotNull(message = "卖家 ID 不能为空")
    private Long sellerId;

    // 商品标题（必传，长度限制）
    @NotBlank(message = "标题不能为空")
    @Size(max = 50, message = "标题长度不能超过 50 个字符")
    private String title;

    // 商品类型（必传，枚举类型）
    @NotNull(message = "商品类型不能为空")
    private VehicleType type;

    // 商品描述（必传）
    @NotBlank(message = "描述不能为空")
    @Size(max = 500, message = "描述长度不能超过 500 个字符")
    private String description;

    // 价格（必传，正数）
    @NotNull(message = "价格不能为空")
    @Positive(message = "价格必须为正数")
    private BigDecimal price;

    // 里程数（必传，非负数）
    @NotNull(message = "里程数不能为空")
    @Min(value = 0, message = "里程数不能为负数")
    private Integer mileage;

    // 位置（必传）
    @NotBlank(message = "位置不能为空")
    @Size(max = 100, message = "位置长度不能超过 100 个字符")
    private String location;

    // 图片 URL 列表（可为空，但需保证类型正确）
    private List<String> imageUrls;

    // 商品类型枚举（根据实际业务扩展）
    public enum VehicleType {
        BICYCLE("自行车"),
        ELECTRIC_BIKE("电动车");

        private final String description;

        VehicleType(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }
}
