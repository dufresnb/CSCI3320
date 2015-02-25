//
//  CalculatorBrain.h
//  Calculator
//
//  Created by Brad on 1/28/15.
//  Copyright (c) 2015 edu.ucdenevr.calculator.dufresnb. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface CalculatorBrain : NSObject

- (void)pushOperand:(double)operand;
- (void)clear;
- (double)performOperation:(NSString *)operation;

@end
  