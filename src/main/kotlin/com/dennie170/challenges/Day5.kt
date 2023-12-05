package com.dennie170.challenges

import com.dennie170.Day
import java.util.regex.Pattern

class Day5 : Day<Long>(5) {
    private val input = """
seeds: 3127166940 109160474 3265086325 86449584 1581539098 205205726 3646327835 184743451 2671979893 17148151 305618297 40401857 2462071712 203075200 358806266 131147346 1802185716 538526744 635790399 705979250

seed-to-soil map:
931304316 1786548802 232453384
3500539319 2322065235 6421609
496396007 147739714 266329192
3169724489 768672891 39526579
3689153715 1361862036 346985
1936948751 3328259881 542896984
3209251068 3154345676 173914205
1163757700 2814318523 24125066
2484210664 1362209021 231487475
3991904247 2133571422 188493813
1187882766 4045525873 83717994
861951350 3084992710 69352966
2715698139 2838443589 43714032
3830303258 4025104215 20421658
768672891 1268583577 93278459
4180398060 2019002186 114569236
3689500700 1593696496 10659519
1271600760 808199470 460384107
166497091 526585653 102729094
3700160219 3894961176 130143039
2966889400 2882157621 202835089
147739714 414068906 18757377
3850724916 4133608796 141179331
2759412171 2328486844 183672918
2479845735 4129243867 4364929
3480360150 4274788127 20179169
402636637 432826283 93759370
3383165273 2717123646 97194877
3506960928 1604356015 182192787
269226185 629314747 133410452
2943085089 3871156865 23804311
1731984867 2512159762 204963884

soil-to-fertilizer map:
3368312743 826425240 243745914
1045038113 3682756471 174490549
3931158487 1530223690 363808809
1219528662 2460222182 131099318
3020480207 1894032499 63879875
121779694 248970341 36319877
1993634034 2662348686 86667553
3612058657 1323325837 196530127
1531175223 2604354699 57993987
158099571 121779694 127190647
1867147432 3317666386 126486602
2080301587 2768963716 548702670
1402482267 1070171154 21180243
2959841028 4051272297 60639179
834756529 1966243663 128160296
3911211010 2749016239 19947477
962916825 3857247020 82121288
2629004257 3444152988 238603483
826425240 1957912374 8331289
1350627980 3939368308 51854287
1589169210 4214533702 80433594
2867607740 2094403959 92233288
1669602804 1125781209 197544628
3084360082 1519855964 10367726
1483712212 1091351397 34429812
3094727808 2186637247 273584935
1423662510 3991222595 60049702
3808588784 4111911476 102622226
1518142024 2591321500 13033199

fertilizer-to-water map:
206818393 1973789958 18543481
2641351404 1992333439 41420268
58400970 2574944960 107826712
3710426911 4065366707 42793360
4217161704 4274048011 20919285
1926695368 705931711 328031436
1449580741 1210970895 50549447
907984567 1421828853 15115545
769748018 1108192216 102778679
451427938 35457870 38201654
2254726804 2033892789 137829519
923239194 1513967644 270588891
3753220271 4108160067 165887944
499804857 310274559 109862756
3061525238 3535532059 426476055
1193828085 73659524 196024324
872526697 0 35457870
1766386857 1261520342 160308511
4057593930 3283950856 159567774
1389852409 646203379 59728332
3919108215 3962008114 103358593
1577153434 1784556535 189233423
4022466808 3443518630 35127122
489629592 1098016951 10175265
923100112 2033753707 139082
2392556323 2390203683 158894869
1500130188 1436944398 77023246
2577297600 1033963147 64053804
609667613 2171722308 160080405
3488001293 3061525238 222425618
2551451192 2549098552 25846408
4238080989 3478645752 56886307
166227682 269683848 40590711
0 2331802713 58400970
225361874 420137315 226066064

water-to-light map:
1833244152 0 764535859
212138399 2132863085 224047237
445686952 1600446740 163005122
3322180377 2914685303 488586806
2739726430 3712513349 582453947
3946546331 3589340640 8839399
1441711040 799272484 245821386
1038755613 1763451862 6623730
608692074 1587251997 13194743
701103180 2356910322 39153476
1687532426 1045093870 145711726
2597780011 764535859 34736625
740256656 1490869662 54307168
0 1920724686 212138399
2632516636 1545176830 9229765
668257778 1554406595 32845402
3955385730 2739726430 39179725
4180633986 3598180039 114333310
3810767183 2778906155 135779148
1291061946 1770075592 150649094
436185636 1481368346 9501316
1045379343 2396063798 245682603
794563824 1237176557 244191789
621886817 1190805596 46370961
3994565455 3403272109 186068531

light-to-temperature map:
432141642 1268486741 19474646
3617581823 3276436954 357008111
3505110084 3786131308 49942802
0 1287961387 432141642
3096011130 1808659179 409098954
1347993824 2675880000 161612192
3019335150 3199760974 76675980
3555052886 3137232037 62528937
2778092757 1720103029 88556150
451616288 2217758133 458121867
1509606016 0 1268486741
909738155 3836074110 138515824
1048253979 2837492192 299739845
2866648907 3633445065 152686243

temperature-to-humidity map:
646729740 1519504972 559297346
1894539176 2990410634 44298872
232257988 972432123 414471752
2277879451 278205785 108711195
1775790220 132298732 118748956
3371687162 2663455233 326955401
1612056920 272509895 5695890
1208383109 3703499740 147415518
4070380190 4053129082 69974785
4155541210 3305585510 139426086
81956384 386916980 150301604
3987543096 896459472 75972651
2148980475 1386903875 128898976
1617752810 3445011596 154599732
4063515747 2078802318 6864443
2392568787 3599611328 101532389
2386590646 4123103867 5978141
2494101176 2122546980 187027686
2681128862 2085666761 36880219
4140354975 2648268998 15186235
1772352542 3051742077 3437678
1355798627 3850915258 202213824
3720104770 3055179755 250405755
3032992830 2309574666 338694332
1206027086 3701143717 2356023
1938838048 537218584 44257139
1558012451 81956384 50342348
3970510525 3034709506 17032571
1608354799 1515802851 3702121
1983095187 4129082008 165885288
3698642563 251047688 21462207
2718009081 581475723 314983749

humidity-to-location map:
971626884 4275486551 19480745
1218249913 2090555906 502249162
2914848039 2902831882 224865747
3341591733 2819947352 82884530
991107629 2592805068 227142284
3424476263 606585628 95279547
4279176998 2064757318 10971709
3139713786 4068790015 201877947
606585628 701865175 365041256
3534582689 3291885426 744594309
1916997152 1066906431 997850887
1752809355 3127697629 164187797
1720499075 4036479735 32310280
4290148707 4270667962 4818589
3519755810 2075729027 14826879
    """.trimIndent()

    override fun part1(): Long {
        val almanac = Almanac.parse(input)

        var seeds = almanac.seeds

        for (map in almanac.maps) {
            seeds = seeds.map {
                mapItemToNext(it, map.value)
            }
        }

        return seeds.min()
    }

    private fun mapItemToNext(item: Long, map: List<Range>): Long {
        val sources = map.map {
            it.source.rangeUntil(it.source + it.length)
        }

        val destinations = map.map {
            it.destination.rangeUntil(it.destination + it.length)
        }

        val destinationIndex = sources.indexOfFirst { it.contains(item) }

        if (destinationIndex == -1) return item

        val index = item - sources[destinationIndex].first

        val added = destinations[destinationIndex].first + index

        return if (destinations[destinationIndex].contains(added)) added else item
    }

    override fun part2(): Long {
        return 0
    }


    data class Almanac(val seeds: List<Long>, val maps: Map<String, List<Range>>) {
        companion object {
            fun parse(input: String): Almanac {
                val seeds = input.substring(7)
                    .substringBefore('\n')
                    .split(' ')
                    .map(String::toLong)

                val maps = mutableMapOf<String, List<Range>>()
                val matcher = Pattern.compile("([a-z-]+ map:\\n[0-9\\s]+)").matcher(input)

                while (matcher.find()) {
                    val lines = matcher.group(1).lines()
                    val name = lines.removeFirst().substringBefore(' ')
                    val ranges = lines.filter(String::isNotEmpty).map {
                        val line = it.split(' ').filter(String::isNotEmpty)
                        Range(line[0].toLong(), line[1].toLong(), line[2].toInt())
                    }

                    maps[name] = ranges
                }

                return Almanac(seeds, maps)
            }
        }
    }


    data class Range(val destination: Long, val source: Long, val length: Int)
}
