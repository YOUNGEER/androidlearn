package com.wenyi.androiders.recycleview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.wenyi.androiders.R;
import com.wenyi.recycle.meizucontact.CommonUtil;
import com.wenyi.recycle.meizucontact.decoration.ContactAdapter;
import com.wenyi.recycle.meizucontact.decoration.ContactBean;
import com.wenyi.recycle.meizucontact.decoration.MeizuContactItemDecoration;
import com.wenyi.recycle.meizucontact.decoration.SideBar;
import com.wenyi.recycle.meizucontact.itemAnimation.SlideInOutLeftItemAnimator;

import java.util.ArrayList;
import java.util.List;

public class RecycleIndexActivity extends AppCompatActivity {
    RecyclerView mrecycyle;
    MeizuContactItemDecoration decoration;
    LinearLayoutManager layoutManager;
    private SideBar side_bar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recycle_index);
        mrecycyle= (RecyclerView) findViewById(R.id.recycle);
        //侧边导航栏
        side_bar = (SideBar) findViewById(R.id.side_bar);

        decoration=new MeizuContactItemDecoration(RecycleIndexActivity.this);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mrecycyle.setLayoutManager(layoutManager);
        mrecycyle.addItemDecoration(decoration);
        mrecycyle.setItemAnimator(new SlideInOutLeftItemAnimator(mrecycyle));
        initDatas();

    }

    private void initDatas() {

        final List<ContactBean> nameList = new ArrayList<>();
        String[] names = {"刀锋意志 艾瑞莉娅 （刀锋）","嗜血猎手 沃里克 （狼人）","赏金猎人 厄运晓姐 （好运姐）",
                "英勇投弹手 库奇 （飞机）","复仇焰魂 布兰德 （火人）","暗影之拳 阿卡丽 （Akali）",
                "天启者 卡尔玛 （Karma）","盲僧 李青 （李青）","狂暴之心 凯南 （凯南）",
                "玛西亚之力 盖伦 （盖伦）","寒冰射手 艾希 （爱射）","蛮族之王 泰达米尔 （蛮王）",
                "宝石骑士 塔里克 （奶爸）","邪恶小法师 维迦 （维迦）","武器大师 贾克斯 （JAX）",
                "暗夜猎手 薇恩 （VN）","堕落天使 莫甘娜 （堕天使）","虚空先知 玛尔扎哈 （先知）",
                "时光守护者 基兰 （时光老头)","机械公敌 兰博 （喷火娃）","诅咒巨魔 特朗德尔 （巨魔）",
                "炼金术士 辛吉德 （炼金）","策士统领 斯维因 （乌鸦）","魔蛇之拥 卡西奥佩娅 （蛇女）","死亡颂唱者 卡尔萨斯 （死歌）",
                "黑暗之女 安妮 （Annie）","皮城女警 凯特琳 （女警）","寡妇制造者 伊芙琳 （EVE）",
                "瘟疫之源 图奇 （老鼠）","审判天使 凯尔 （天使）","虚空恐惧 科'加斯 （虫子）",
                "狂战士 奥拉夫 （狂战）","殇之木乃伊 阿木木 （木木）","哨兵之殇 加里奥 （蝙蝠侠）",
                "蒸汽机器人 布里茨 （机器人）","大发明家 黑默丁格 （大头）","卡牌大师 崔斯特 （卡牌）",
                "熔岩巨兽 墨菲特 （石头人）","沙漠死神 内瑟斯 （狗头）","深渊巨口 克格'莫 （大嘴）","无极剑圣 易 （易大师）",
                "德邦总管 赵信 （赵信）","狂野女猎手 奈德丽 （豹女）","牛头酋长 阿利斯塔 （老牛）","披甲龙龟 拉莫斯 （龙龟）",
                "首领之傲 厄加特 （螃蟹）","暮光之眼 慎 （shen）","流浪法师 瑞兹 （瑞兹）","冰晶凤凰 艾尼维亚 （凤凰）",
                "不祥之刃 卡特琳娜 （KT）","野兽之灵 乌迪尔 （德鲁伊）","亡灵勇士 赛恩 （斧头哥）","恶魔小丑 萨科 （小丑）",
                "诡术妖姬 乐芙兰 （妖姬）","永恒梦魇 魔腾 （梦魇）","猩红收割者 弗拉基米尔 （吸血鬼）","战争女神 希维尔 （轮子妈）",
                "祖安狂人 蒙多医生 （蒙多）","扭曲树精 茂凯 （树人）","战争之王 潘森 （斯巴达）",
                "钢铁大使 波比 （波比）","光辉女郎 拉克丝 （lakes）","众星之子 索拉卡 （SORAKA，奶妈）",
                "琴瑟仙女 娑娜 （SONA）","探险家 伊泽瑞尔 （EZ）","末日使者 费德提克 （稻草人）","荒漠屠夫 雷克顿 （鳄鱼）",
                "酒桶 古拉加斯 （酒桶)","虚空行者 卡萨丁 （凯撒丁）","风暴之怒 迦娜 （风女）",
                "迅捷斥候 提莫 （提莫）","发条魔灵 奥莉安娜 （发条）","德玛西亚皇子 嘉文四世 （皇子）",
                "金属大师 莫德凯撒 （铁男）","雪人骑士 努努 （NUNU）","海洋之灾 普朗克 （船长）","麦林炮手 崔丝塔娜 （小炮）"};

        for (String name : names) {
            ContactBean bean = new ContactBean();
            bean.setName(name);
            nameList.add(bean);
        }
        //对数据源进行排序
        CommonUtil.sortData(nameList);
        //返回一个包含所有Tag字母在内的字符串并赋值给tagsStr
        String tagsStr = CommonUtil.getTags(nameList);
        ContactAdapter mAdapter=new ContactAdapter(RecycleIndexActivity.this);
        decoration.setDatas(nameList, tagsStr);
        side_bar.setIndexStr(tagsStr);
        mAdapter.addAll(nameList);
        mrecycyle.setAdapter(mAdapter);

        side_bar.setIndexChangeListener(new SideBar.indexChangeListener() {
            @Override
            public void indexChanged(String tag) {
                if (TextUtils.isEmpty(tag) || nameList.size() <= 0) return;
                for (int i = 0; i < nameList.size(); i++) {
                    if (tag.equals(nameList.get(i).getIndexTag())) {
                        layoutManager.scrollToPositionWithOffset(i, 0);
                        return;
                    }
                }
            }
        });
    }


}
