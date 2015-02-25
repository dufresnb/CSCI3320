//
//  CalculatorBrain.m
//  Calculator
//
//  Created by Brad on 1/28/15.
//  Copyright (c) 2015 edu.ucdenevr.calculator.dufresnb. All rights reserved.
//

#import "CalculatorBrain.h"

@interface CalculatorBrain()
@property (nonatomic, strong) NSMutableArray *operandStack;
@end

@implementation CalculatorBrain

@synthesize operandStack = _operandStack;

- (NSMutableArray *)operandStack
{
    if(!_operandStack)
    {
        _operandStack = [[NSMutableArray alloc] init];
    }
    return _operandStack;
}

//when a new number is added to the stack
- (void)pushOperand:(double)operand
{
    NSNumber *operandObject = [NSNumber numberWithDouble:operand];
    [self.operandStack addObject:operandObject];
}

//get a number off the stack
- (double)popOperand
{
    NSNumber *operandObject = [self.operandStack lastObject];
    if(operandObject) [self.operandStack removeLastObject];
    return [operandObject doubleValue];
}

//empty the stack
- (void)clear
{
    while(self.operandStack.count > 0)[self popOperand];
}

//do operation on the top numbers on the stack
- (double)performOperation:(NSString *)operation
{
    double result = 0;
    
    if([operation isEqualToString:@"+"])//addition
    {
        result = [self popOperand] + [self popOperand];
    } else if([operation isEqualToString:@"*" ])//multiplication
    {
        result = [self popOperand] * [self popOperand];
    } else if([operation isEqualToString:@"-" ])//subtraction
    {
        double subtrahend = [self popOperand];
        result = [self popOperand] - subtrahend;
    } else if([operation isEqualToString:@"/" ])//division
    {
        double divisor = [self popOperand];
        result = [self popOperand] / divisor;
        if(divisor==0)result=0;
    } else if([operation isEqualToString:@"sin" ])//sine functio
    {
        double sineOperand = [self popOperand];
        result = sin(sineOperand);
    } else if([operation isEqualToString:@"cos" ])//cosine function
    {
        double cosineOperand = [self popOperand];
        result = cos(cosineOperand);
    } else if([operation isEqualToString:@"sqrt" ])//square root function
    {
        double sqrtOperand = [self popOperand];
        result = sqrt(sqrtOperand);
    } else if([operation isEqualToString:@"π" ])//returns the value of pi
    {
        double pi = M_PI;
        result = pi;
    }
    
    [self pushOperand:result];
    
    return result;
}

@end
