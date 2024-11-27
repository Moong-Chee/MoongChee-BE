package project.MoongChee.domain.user.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Department {
    SW("소프트웨어전공"),
    AI("인공지능전공"),
    COMPUTER_SCIENCE("컴퓨터공학과"),
    INDUSTRIAL_ENGINEERING("산업공학과"),
    VISUAL_DESIGN("시각디자인학과"),
    BUSINESS("경영학과"),
    ECONOMICS("경제학과");   // 더 필요한 학과는 추후 추가할 예정

    private final String value;
}
