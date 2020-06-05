package com.maduro.cas.unit.orchestration.domain;

import java.lang.reflect.Field;
import java.util.Arrays;

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

	public Double parseValueWonToDouble() {
		return (this.getValue_won() == null || this.getValue_won().isEmpty()) ? 0.
				: Double.parseDouble(this.getValue_won());
	}

	public boolean hasAllValidFields() {

		boolean[] return_ = { true };

		try {
			Field[] notNullFields = { this.getClass().getDeclaredField("game") };

			Arrays.asList(notNullFields).forEach(field -> {
				boolean accessible = field.isAccessible();// canAccess(this);
				field.setAccessible(true);

				try {
					Object value = field.get(this);

					if (value == null) {
						return_[0] = false;
						return;
					}
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}

				field.setAccessible(accessible);
			});

		} catch (NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}

		return return_[0];
	}
}