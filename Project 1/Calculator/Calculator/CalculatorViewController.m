//
//  CalculatorViewController.m
//  Calculator
//
//  Created by Bradley Dufresne
//  Copyright (c) 2015 edu.ucdenevr.calculator.dufresnb. All rights reserved.
//

#import "CalculatorViewController.h"
#import "CalculatorBrain.h"

@interface CalculatorViewController ()
@property (nonatomic) BOOL userIsInTheMiddleOfEnteringANumber;
@property (nonatomic) BOOL decimalIsUsed;
@property (nonatomic) BOOL firstKeyIsPressed;
@property (nonatomic, strong) CalculatorBrain *brain;
@end

@implementation CalculatorViewController

@synthesize brain=_brain;

- (CalculatorBrain *)brain
{
    if(!_brain) _brain = [[CalculatorBrain alloc] init];
    return _brain;
}

//When numbers are pressed, digitPressed appends the number to the display
- (IBAction)digitPressed:(UIButton *)sender {
    NSString *digit = [sender currentTitle];
    if(self.userIsInTheMiddleOfEnteringANumber)
    {
        self.display.text = [self.display.text stringByAppendingString:digit];
        self.fullDisplay.text = [self.fullDisplay.text stringByAppendingString:digit];
    } else
    {
        self.display.text = digit;
        self.userIsInTheMiddleOfEnteringANumber=YES;
        if(!self.firstKeyIsPressed)
        {
            self.firstKeyIsPressed = YES;
            self.fullDisplay.text = digit;
        }
        else
        {
            self.fullDisplay.text = [self.fullDisplay.text stringByAppendingString:digit];
        }
    }
    if(self.fullDisplay.text.length>30)
    {
        self.fullDisplay.text=[self.fullDisplay.text substringFromIndex:2];
    }
}

//enterPressed pushes the nuber from the display onto the stack
- (IBAction)enterPressed
{
    if(self.firstKeyIsPressed)
    {
        self.fullDisplay.text = [self.fullDisplay.text stringByAppendingString:@" "];
    }
    [self.brain pushOperand:[self.display.text doubleValue]];
    self.userIsInTheMiddleOfEnteringANumber = NO;
    self.decimalIsUsed = NO;
    if(self.fullDisplay.text.length>30)
    {
        self.fullDisplay.text=[self.fullDisplay.text substringFromIndex:2];
    }
}

//pushes current number onto stack and submits the operation to CalculatorBrain to be resolved
- (IBAction)operationPressed:(UIButton *)sender
{
    if(!self.firstKeyIsPressed){self.firstKeyIsPressed = YES;}
    if(self.userIsInTheMiddleOfEnteringANumber)
    {
        [self enterPressed];
    }
    NSString *operation = [sender currentTitle];
    double result = [self.brain performOperation:operation];
    self.display.text = [NSString stringWithFormat:@"%g", result];
    self.fullDisplay.text = [self.fullDisplay.text stringByAppendingString:operation];
    self.fullDisplay.text = [self.fullDisplay.text stringByAppendingString:@"="];
    if(self.fullDisplay.text.length>30)
    {
        self.fullDisplay.text=[self.fullDisplay.text substringFromIndex:3];
    }
}

//checks if a decimal is currently allowed and appends one to the display if it is
- (IBAction)decimalPressed
{
    if(!self.firstKeyIsPressed){self.firstKeyIsPressed = YES;}
    if(!self.decimalIsUsed)
    {
        if(!self.userIsInTheMiddleOfEnteringANumber)
        {
            self.display.text = @"0";
        }
        self.display.text = [self.display.text stringByAppendingString:@"."];
        self.userIsInTheMiddleOfEnteringANumber = YES;
        self.decimalIsUsed = YES;
        self.fullDisplay.text = [self.fullDisplay.text stringByAppendingString:@"."];
    }
}

//adds or removes a '-' frm the beggining of the display
- (IBAction)negatePressed
{
    if(self.firstKeyIsPressed)
    {
        if(self.display.text.length>1 && [[self.display.text substringToIndex:2] isEqual:@"-"])
        {
            self.display.text = [self.display.text substringFromIndex:2];
        }
        else
        {
            NSString *temp = @"-";
            temp = [temp stringByAppendingString:self.display.text];
            self.display.text = temp;
        }
        self.fullDisplay.text = [self.fullDisplay.text stringByAppendingString:@"±"];
    }
}

//resets settings and clears display and the history display
- (IBAction)clearPressed
{
    [self.brain clear];
    self.firstKeyIsPressed = NO;
    self.fullDisplay.text = @"0";
    self.display.text = @"0";
    self.userIsInTheMiddleOfEnteringANumber = NO;
    self.decimalIsUsed = NO;
}

//removes a character from the display or resets the display to default and saves a backspace to the history
- (IBAction)backspacePressed
{
    self.fullDisplay.text = [self.fullDisplay.text stringByAppendingString:@"⌫"];
    if(self.userIsInTheMiddleOfEnteringANumber)
    {
        if([[self.display.text substringFromIndex:self.display.text.length-1] isEqual:@"."])
        {
            self.decimalIsUsed = NO;
        }
        self.display.text = [self.display.text substringToIndex:self.display.text.length-1];
        if(self.display.text.length<1)
        {
            self.display.text = @"0";
            self.userIsInTheMiddleOfEnteringANumber = NO;
        }
    }
}


@end
