package com.maduro.cas.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@lombok.Data
@Builder
@NoArgsConstructor
public class HandDataModel {
	private String game;
	private String hand;
	private String hand_position;
	private String user_name;
	private String card_sequence;
	private String value_won;
	private String board;
	private String all_in_action_street;
	private String action_pre_flop;
	private String value_action_pre_flop;
	private String action_flop;
	private String value_action_flop;
	private String action_turn;
	private String value_action_turn;
	private String action_river;
	private String value_action_river;
	private String bb;
	private String street_ended;
	private String show_down;
	private String level;

}