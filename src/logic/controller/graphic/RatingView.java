package logic.controller.graphic;
import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class RatingView extends HBox{

	private Boolean isEditable = true;
	private Integer maxStars = 0;
	private Integer ratingPoints = 0;
	
	private Image emptyRatingElement;
	private Image semiRatingElement;
	private Image fullRatingElement;
	
	private ArrayList<ImageView> stars = new ArrayList<>();
	
	public void setEditable(Boolean editable) {
		this.isEditable = editable;
	}
	
	public RatingView(Integer maxStars) {
		this.maxStars = maxStars;
		emptyRatingElement = new Image(getClass().getResourceAsStream("/logic/view/assets/images/empty-star.png"));
		semiRatingElement = new Image(getClass().getResourceAsStream("/logic/view/assets/images/semi-star.png"));
		fullRatingElement = new Image(getClass().getResourceAsStream("/logic/view/assets/images/full-star.png"));
	
		Integer position;
		for(position = 0; position < maxStars; position++) {
			ImageView ratingElementImage = new ImageView(emptyRatingElement);
			ratingElementImage.setFitHeight(40.0);
			ratingElementImage.setPreserveRatio(true);
			
			//Aggiunta comportamento
			final Integer finalPosition = position;
			ratingElementImage.setOnMouseClicked((MouseEvent e) -> {
				if(Boolean.TRUE.equals(isEditable)) {
					this.ratingPoints = (finalPosition+1) * 2;
					setValue(this.ratingPoints);
				}
			});
			ratingElementImage.setOnMouseExited((MouseEvent e) ->{
				if(Boolean.TRUE.equals(isEditable)) {
					setValue(this.ratingPoints);
				}
			});
			ratingElementImage.setOnMouseEntered((MouseEvent e) ->{
				if(Boolean.TRUE.equals(isEditable)) {
					setValue((finalPosition+1) * 2);
				}
			});
			
			stars.add(ratingElementImage);
			
		}
		
		getChildren().addAll(stars);
		setValue(0);
	}
	
	public Integer getValue() {
		return this.ratingPoints;
	}
	
	public void setValue(Integer ratingPoints) {
		if(ratingPoints > maxStars * 2) {
			ratingPoints = maxStars * 2;
		}
		
		Integer position = 0;
		
		Integer half = ratingPoints % 2;
		Integer valueInStars = ratingPoints / 2;
		
		for(position = 0; position < valueInStars; position++) {
			stars.get(position).setImage(fullRatingElement);
		}
		for(position = valueInStars; position < maxStars; position++) {
			stars.get(position).setImage(emptyRatingElement);
		}
		if(half == 1) {
			stars.get(valueInStars).setImage(semiRatingElement);
		}
		
		
	}
	
}
