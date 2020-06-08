package com.maduro.cas.service.handdata;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.maduro.cas.domain.CriticalHandOutcomeEnum;
import com.maduro.cas.domain.HandDataModel;
import com.maduro.cas.service.handdata.exception.WinnerHandNotFoundException;
import com.yg2288.service.HandRankingService;

public class HandDataService {

	public CriticalHandOutcomeEnum analyzeHands(List<HandDataModel> listHandDataModel) throws Exception {

		List<HandDataModel> winnerHandsList = getWinnerHands(listHandDataModel);

		if (winnerHandsList.size() > 1) {
			return CriticalHandOutcomeEnum.TIED;
		} else {

			if (winnerHandsList.size() == 0) {
				throw new WinnerHandNotFoundException();
			}

			String winnerHandCards = winnerHandsList.get(0).getCard_sequence();
			String[] cardsArray = getCards(listHandDataModel);
			String bestCards = getBestCards(cardsArray);

			boolean bestHandWon = winnerHandCards.contentEquals(bestCards);

			return bestHandWon ? CriticalHandOutcomeEnum.BEST_WIN : CriticalHandOutcomeEnum.WORST_WIN;
		}
	}

	private String getBestCards(String[] cardsArray) throws Exception {

		HandRankingService handRankingService = new HandRankingService();
		String bestHand = handRankingService.getBestStringHand(Arrays.asList(cardsArray));

		String numberTen = "10";
		String tLetter = "T";
		return bestHand.replace(numberTen, tLetter);
	}

	private String[] getCards(List<HandDataModel> listHandDataModel) {

		ArrayList<String> returnList = new ArrayList<String>();
		listHandDataModel.stream().forEach(handDataModel -> {
			String cards = handDataModel.getCard_sequence();
			returnList.add(cards);
		});

		return returnList.toArray(new String[returnList.size()]);
	}

	private List<HandDataModel> getWinnerHands(List<HandDataModel> listHandDataModel) {

		List<HandDataModel> listWinnerHands = new ArrayList<>();

		Double[] lastValue = { 0. };
		listHandDataModel.forEach(handDataModel -> {

			Double valueWonParsed = handDataModel.parseValueWonToDouble();

			if (valueWonParsed != 0) {
				if (valueWonParsed < lastValue[0]) {
					return;
				}

				if (valueWonParsed > lastValue[0]) {
					listWinnerHands.clear();
				}
				listWinnerHands.add(handDataModel);
				lastValue[0] = valueWonParsed;

			}

		});
		return listWinnerHands;
	}
}