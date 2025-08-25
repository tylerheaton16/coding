module top(
  input clk,
  input reset,
  output [7:0] result
);

  wire [7:0] internal_sig;
  wire [4:0] cpu_out;
  
  // Instantiate CPU
  cpu cpu_inst(
    .sig1_i(internal_sig),
    .sig1_0(cpu_out)
  );
  
  // Connect to output
  assign result = {3'b0, cpu_out};
  assign internal_sig = 8'hAA;

endmodule