package com.cadre.controller.convertor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import com.cadre.model.excel.AbstractCheckRepeatExcelDataConvertor;
import com.cadre.model.excel.DataErrorMsg;
import com.cadre.model.excel.FileCheckerException;
import com.cadre.model.excel.utils.PoiUtil;
import com.cadre.model.utils.DigestUtil;
import com.cadre.model.view.ViewFunction;
import com.cadre.pojo.Train;
import com.cadre.pojo.User;

public class UserTrainConvertor extends
		AbstractCheckRepeatExcelDataConvertor<Train> {
	private Logger logger = LogManager.getLogger(UserTrainConvertor.class);
	private static final String beginDate = "开始时间";
	private static final String endDate = "结束时间";
	private static final String trainingName = "教育培训名称";
	private static final String organizer = "主办单位";
	private static final String trainingPeriod = "教育培训期数";
	private static final String trainingPlace = "教育培训地点";
	/**
	 * 在数据库中已经存在的用户信息
	 */
	private Map<String, Train> trainMap;
	/**
	 * 标题行，key标题名,value为标题所在列
	 */
	private Map<String, Integer> TITLE_MAP = initTitleMap();

	public UserTrainConvertor(Map<String, Train> map) {
		this.trainMap = map;
	}

	public UserTrainConvertor(List<Train> list) {
		this(toMap(list));
	}

	private static Map<String, Train> toMap(List<Train> list) {
		HashMap<String, Train> map = new HashMap<String, Train>();
		if (list == null || list.size() == 0) {
			return map;
		}
		for (Train train : list) {
			if (train == null)
				continue;
			map.put(train.getTrainingName(), train);
		}
		return map;
	}

	/**
	 * 初始化标题map，值都为空
	 * 
	 * @return
	 */
	private static final Map<String, Integer> initTitleMap() {
		Map<String, Integer> titleMap = new HashMap<String, Integer>();
		titleMap.put(beginDate, null);
		titleMap.put(endDate, null);
		titleMap.put(trainingName, null);
		titleMap.put(organizer, null);
		titleMap.put(trainingPeriod, null);
		titleMap.put(trainingPlace, null);
		return titleMap;
	}

	/**
	 * 重写readTitleRow函数，读取标题行，用来确认标题对应的列数
	 */
	@Override
	public void readTitleRow(Row headRow) {
		Iterator<Cell> cellIterator = headRow.cellIterator();
		Cell cell = null;
		String title;
		while (cellIterator.hasNext()) {
			cell = cellIterator.next();
			if (cell != null) {
				title = PoiUtil.getTrim2EmptyText(cell);
				if (TITLE_MAP.containsKey(title)) {
					TITLE_MAP.put(title, cell.getColumnIndex());
				}
			}
		}
	}

	/**
	 * 检查文件第一行（excel标题行）
	 * 
	 * @param headRow
	 * @return
	 */
	@Override
	public void checkTitleRow(Row headRow) throws FileCheckerException {
		Set<Entry<String, Integer>> entrySet = TITLE_MAP.entrySet();
		StringBuffer sb = new StringBuffer();
		for (Entry<String, Integer> e : entrySet) {
			if (e.getValue() == null) {
				sb.append(",不存在[" + e.getKey() + "]列");
			}
		}
		if (sb.length() > 0) {
			throw new FileCheckerException(sb.substring(1));
		}
	}

	@Override
	protected String getRepeatKey(Row row) {
		return PoiUtil.getTrim2EmptyText(row.getCell(TITLE_MAP
				.get(trainingName)));// 根据培训项目名称来判断是否重复
	}

	/**
	 * 检查数据行是否有错，如果有错，则返回错误的DataErrorMsg对象
	 */
	@Override
	protected DataErrorMsg checkDataRow(Row row) {
		StringBuffer sb = new StringBuffer();

		String begindate = PoiUtil.getTrim2EmptyText(row.getCell(TITLE_MAP
				.get(beginDate)));
		String enddate = PoiUtil.getTrim2EmptyText(row.getCell(TITLE_MAP
				.get(endDate)));
		String trainname = PoiUtil.getTrim2EmptyText(row.getCell(TITLE_MAP
				.get(trainingName)));
		String orString = PoiUtil.getTrim2EmptyText(row.getCell(TITLE_MAP
				.get(organizer)));
		String trainperiod = PoiUtil.getTrim2EmptyText(row.getCell(TITLE_MAP
				.get(trainingPeriod)));
		String trainplace = PoiUtil.getTrim2EmptyText(row.getCell(TITLE_MAP
				.get(trainingPlace)));

		int beginIndex = TITLE_MAP.get(beginDate);
		int endIndex = TITLE_MAP.get(endDate);
		int nameIndex = TITLE_MAP.get(trainingName);
		int orIndex = TITLE_MAP.get(organizer);
		int periodIndex = TITLE_MAP.get(trainingPeriod);
		int placeIndex = TITLE_MAP.get(trainingPlace);

		if (StringUtils.isBlank(begindate)) {
			sb.append("第" + (beginIndex + 1) + "列[" + beginDate + "]不能为空,");
		}
		if (StringUtils.isBlank(enddate)) {
			sb.append("第" + (endIndex + 1) + "列[" + endDate + "]不能为空,");
		}
		if (StringUtils.isBlank(trainname)) {
			sb.append("第" + (nameIndex + 1) + "列[" + trainingName + "]不能为空,");
		}
		if (StringUtils.isBlank(orString)) {
			sb.append("第" + (orIndex + 1) + "列[" + organizer + "]不能为空,");
		}
		sb.append(checkCellNotEmpty(row, beginDate));
		sb.append(checkCellNotEmpty(row, endDate));
		sb.append(checkCellNotEmpty(row, trainingName));
		sb.append(checkCellNotEmpty(row, trainingPlace));
		sb.append(checkCellNotEmpty(row, trainingPeriod));
		sb.append(checkCellNotEmpty(row, trainingPlace));
		// 检查完毕
		if (StringUtils.isNotBlank(sb.toString())) {
			return new DataErrorMsg(sb.toString(), row.getRowNum() + 1);
		}
		return null;
	}

	/**
	 * 检查单元格非空
	 * 
	 * @param row
	 * @param cellName
	 * @return
	 */
	private String checkCellNotEmpty(Row row, String cellName) {
		int columnIndex = TITLE_MAP.get(cellName);
		Cell cell = row.getCell(columnIndex);
		String content = PoiUtil.getTrim2EmptyText(cell);
		if (StringUtils.isBlank(content)) {
			return "第" + (columnIndex + 1) + "列[" + cellName + "]不能为空,";
		} else {
			return "";
		}
	}

	/**
	 * 将Excel数据转换成User对象
	 */
	@SuppressWarnings("deprecation")
	@Override
	public Train convert(Row row) {
		Train train;
		String trainname = PoiUtil.getTrim2EmptyText(row.getCell(TITLE_MAP
				.get(trainingName)));
		train = trainMap.get(trainname);
		if (train == null) { // 判断要添加的数据在数据库中是否已经存在,为null说明不存在
			train = new Train();
			// 培训名称
			train.setTrainingName(trainname);
		}
		// 主办单位
		String org = PoiUtil.getTrim2EmptyText(row.getCell(TITLE_MAP
				.get(organizer)));
		train.setOrganizer(org);

		// 培训地点
		String trainplace = PoiUtil.getTrim2EmptyText(row.getCell(TITLE_MAP
				.get(trainingPlace)));
		train.setTrainingPlace(trainplace);

		// 培训时长
		String trainperoid = PoiUtil.getTrim2EmptyText(row.getCell(TITLE_MAP
				.get(trainingPeriod)));
		train.setTrainingPeriod(Integer.parseInt(trainperoid));

		String beg = PoiUtil.getTrim2EmptyText(row.getCell(TITLE_MAP
				.get(beginDate)));
		String end = PoiUtil.getTrim2EmptyText(row.getCell(TITLE_MAP
				.get(endDate)));
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			// 开始时间
			train.setBeginDay(formatter.parse(beg));
			// 结束时间
			train.setEndDate(formatter.parse(end));
			// 持续时长
			//train.setDuration((int) ((formatter.parse(end).getTime() - formatter
			//		.parse(beg).getTime()) / (1000 * 60 * 60 * 24) + 0.5));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return train;
	}

}
