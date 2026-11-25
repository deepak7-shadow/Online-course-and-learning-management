import React, { useEffect, useState } from 'react';
import { useParams, Link } from 'react-router-dom';
import { courseApi } from '@/services/api/courseApi';
import { Certificate } from '@/types/course';
import { Card } from '@/components/ui/Card';
import { Button } from '@/components/ui/Button';
import { Award, ShieldCheck, Printer, ArrowLeft } from 'lucide-react';

export const CertificatesView: React.FC = () => {
  const { code } = useParams<{ code?: string }>();
  const [certificates, setCertificates] = useState<Certificate[]>([]);

  useEffect(() => {
    courseApi.getCertificates().then(setCertificates);
  }, []);

  const cert = code ? certificates.find(c => c.certificateCode === code) || certificates[0] : certificates[0];

  return (
    <div className="max-w-4xl mx-auto px-4 py-12">
      <Link to="/student" className="inline-flex items-center gap-1.5 text-xs text-slate-500 hover:text-slate-800 mb-6">
        <ArrowLeft className="w-4 h-4" /> Back to Dashboard
      </Link>

      {cert ? (
        <div className="space-y-6">
          {/* Certificate Credential Canvas */}
          <div className="p-10 sm:p-14 bg-gradient-to-br from-white via-slate-50 to-amber-50/20 border-8 border-slate-900 rounded-3xl shadow-2xl relative text-center">
            <div className="w-16 h-16 rounded-2xl bg-amber-500 text-white flex items-center justify-center mx-auto mb-6 shadow-lg shadow-amber-500/30">
              <Award className="w-10 h-10" />
            </div>

            <p className="text-xs font-bold tracking-widest text-slate-400 uppercase mb-2">EduFlow Learning Academy</p>
            <h1 className="text-3xl sm:text-4xl font-extrabold text-slate-900 mb-4 font-serif">Certificate of Achievement</h1>
            <p className="text-sm text-slate-500 mb-6">This credential proudly certifies that</p>

            <p className="text-3xl font-extrabold text-brand-700 underline decoration-brand-300 decoration-wavy underline-offset-8 mb-6 font-serif">
              {cert.studentName}
            </p>

            <p className="text-sm text-slate-500 max-w-lg mx-auto mb-2">
              has successfully completed all lecture modules, laboratory assignments, and rigorous assessments for
            </p>
            <p className="text-xl font-bold text-slate-900 mb-8">{cert.courseTitle}</p>

            <div className="grid grid-cols-2 gap-8 border-t border-slate-200 pt-8 max-w-md mx-auto text-xs text-slate-600">
              <div>
                <p className="font-semibold text-slate-900">{cert.instructorName}</p>
                <p className="text-slate-400">Lead Instructor</p>
              </div>
              <div>
                <p className="font-semibold text-slate-900">{new Date(cert.issuedAt).toLocaleDateString()}</p>
                <p className="text-slate-400">Issue Date</p>
              </div>
            </div>

            <div className="mt-8 pt-4 flex items-center justify-center gap-2 text-[11px] text-slate-400">
              <ShieldCheck className="w-4 h-4 text-emerald-500" />
              <span>Verifiable ID: <strong className="text-slate-700">{cert.certificateCode}</strong></span>
            </div>
          </div>

          <div className="flex justify-end gap-3">
            <Button onClick={() => window.print()} variant="outline" className="gap-2 cursor-pointer">
              <Printer className="w-4 h-4" /> Print Certificate
            </Button>
          </div>
        </div>
      ) : (
        <Card className="p-12 text-center">
          <Award className="w-12 h-12 text-slate-300 mx-auto mb-3" />
          <h3 className="text-lg font-bold text-slate-800">No Certificates Earned Yet</h3>
          <p className="text-xs text-slate-400 mt-1">Complete 100% of any enrolled course to unlock your credential.</p>
        </Card>
      )}
    </div>
  );
};
