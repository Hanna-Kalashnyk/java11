package com.exadel.discount.entity;

import com.exadel.discount.dto.coupon.BaseCouponDto;
import com.exadel.discount.dto.coupon.CouponDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@Table(name = "coupons")

public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @NotNull
    private LocalDateTime date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull
    private User user;

    public static Coupon from(CouponDto couponDto) {
        Coupon coupon = new Coupon();
        coupon.setDate(couponDto.getDate());
        return coupon;
    }

    public static Coupon from(BaseCouponDto baseCouponDto) {
        Coupon coupon = new Coupon();
        coupon.setDate(baseCouponDto.getDate());
        return coupon;
    }
}
