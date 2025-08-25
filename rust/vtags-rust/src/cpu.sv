`define CPU2_EN;

module cpu (
    input  cpu_sig_i,
    output cpu_sig_o,
    output cpu_checker
);

endmodule

`ifdef CPU2_EN
module cpu2 (
    input  cpu2_sig_i,
    output cpu2_sig_o,
    output cpu2_check
);

  wire cpu_checker;
  assign cpu_check = cpu_checker & cpu2_sig_o;

cpu cpuInst (
      .cpu_sig_i(cpu2_sig_i),
      .cpu_sig_o(cpu2_sig_o),
      .cpu_checker(cpu_checker)
  );
endmodule
`endif

