//
//  CalculatorViewController.h
//  Calculator
//
//  Created by Bradley Dufresne
//  Copyright (c) 2015 edu.ucdenevr.calculator.dufresnb. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface CalculatorViewController : UIViewController
@property (weak, nonatomic) IBOutlet UILabel *display; //main display
@property (weak, nonatomic) IBOutlet UILabel *fullDisplay; //history display

@end
